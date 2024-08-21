package top.mxzero.security.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.mxzero.security.dto.RestData;
import top.mxzero.security.entity.Member;
import top.mxzero.security.entity.UserToken;
import top.mxzero.security.mapper.UserTokenMapper;
import top.mxzero.security.service.MemberService;

import java.security.Principal;
import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
@RequestMapping("/api/users")
@RestController
public class UserOnlineController {
    @Autowired
    private UserTokenMapper tokenMapper;


    @Autowired
    private MemberService memberService;

    @GetMapping("sessions")
    public RestData<List<UserToken>> userOnlineSessionApi(Principal principal, Integer state) {
        Member member = memberService.findByUsername(principal.getName());
        if (member == null) {
            return RestData.success(null);
        }
        QueryWrapper<UserToken> queryWrapper = new QueryWrapper<UserToken>().eq("user_id", member.getId());
        if (state != null) {
            queryWrapper.eq("state", state);
        }
        List<UserToken> tokenList = this.tokenMapper.selectList(queryWrapper);

        return RestData.success(tokenList);
    }
}
