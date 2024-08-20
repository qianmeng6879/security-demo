package top.mxzero.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.mxzero.security.dto.TokenResDTO;
import top.mxzero.security.dto.UserAuthDTO;
import top.mxzero.security.entity.Member;
import top.mxzero.security.entity.UserToken;
import top.mxzero.security.exceptions.ServiceCode;
import top.mxzero.security.mapper.MemberMapper;
import top.mxzero.security.mapper.UserTokenMapper;
import top.mxzero.security.service.TokenService;
import top.mxzero.security.utils.EmailUtil;
import top.mxzero.security.utils.PhoneUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/16
 */
@Service
public class TokenServiceImpl implements TokenService {
    @Autowired
    private UserDetailsService detailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserTokenMapper tokenMapper;
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    @Transactional
    public TokenResDTO<?> createToken(UserAuthDTO dto) {
        Member member = memberMapper.selectOne(new QueryWrapper<Member>().eq("username", dto.getUsername()));

        // 账号密码匹配
        if (member == null || !passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            return new TokenResDTO<>(null, ServiceCode.PASSWORD_INVALID);
        }

        // 账号被锁定
        if (member.getLocked() == 1) {
            return new TokenResDTO<>(null, ServiceCode.ACCOUNT_LOCKED);
        }

        // 用户需要二次认证
        if (member.getEnable2fa() == 1) {
            Map<String, String> map = new HashMap<>();
            if (StringUtils.hasLength(member.getEmail())) {
                map.put("email", EmailUtil.maskEmail(member.getEmail()));
            }

            if (StringUtils.hasLength(member.getPhone())) {
                map.put("phone", PhoneUtil.maskPhoneNumber(member.getPhone()));
            }

            String towFaToken = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(String.format("account:%s:2fa", member.getId()), towFaToken, 10, TimeUnit.MINUTES);

            return new TokenResDTO<>(Map.of("token", towFaToken, "items", map), ServiceCode.ACCOUNT_2FA);
        }

        // 判断用户是否在同一个设备类型上已经有登录信息
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<UserToken>();
        queryWrapper.eq("user_id", member.getId());
        queryWrapper.eq("device_type", dto.getDeviceType().getValue());
        queryWrapper.eq("state", UserToken.TokenState.NORMAL.getState());

        UserToken originToken = tokenMapper.selectOne(queryWrapper);
        if (originToken != null) {
            originToken.setState(UserToken.TokenState.KICK.getState());
            tokenMapper.updateById(originToken);
        }

        // 生成token
        String tokenStr = UUID.randomUUID().toString().replaceAll("-", "");
        Date current = new Date();

        UserToken userToken = new UserToken();
        userToken.setUserId(member.getId());
        userToken.setUsername(member.getUsername());
        userToken.setToken(tokenStr);
        userToken.setExpire(7200L);
        userToken.setState(UserToken.TokenState.NORMAL.getState());
        userToken.setCreatedAt(current);
        userToken.setDeviceType(dto.getDeviceType().getValue());
        userToken.setDeviceFlag(dto.getDeviceFlag());
        tokenMapper.insert(userToken);
        memberMapper.updateLoginTimeByUsername(dto.getUsername(), current);
        return new TokenResDTO<>(tokenStr, ServiceCode.SUCCESS);
    }
}
