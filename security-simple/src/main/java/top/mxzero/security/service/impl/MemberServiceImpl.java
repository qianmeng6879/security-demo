package top.mxzero.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import top.mxzero.security.controller.params.ChangePasswordParam;
import top.mxzero.security.controller.params.PageAndSearch;
import top.mxzero.security.dto.PageDTO;
import top.mxzero.security.dto.UserDTO;
import top.mxzero.security.dto.UserinfoDTO;
import top.mxzero.security.entity.Member;
import top.mxzero.security.entity.MemberRole;
import top.mxzero.security.entity.Role;
import top.mxzero.security.exceptions.ServiceException;
import top.mxzero.security.mapper.MemberMapper;
import top.mxzero.security.mapper.MemberRoleMapper;
import top.mxzero.security.mapper.RoleMapper;
import top.mxzero.security.service.AuthorizeService;
import top.mxzero.security.service.MemberService;
import top.mxzero.security.utils.DeepBeanUtil;
import top.mxzero.security.utils.EmailUtil;
import top.mxzero.security.utils.PhoneUtil;

import java.util.Date;
import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
@Service
@Slf4j
public class MemberServiceImpl implements MemberService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MemberRoleMapper memberRoleMapper;

    @Autowired
    private AuthorizeService authorizeService;

    @Override
    public Long getUserIdByUsername(String username) {
        return null;
    }

    @Override
    public Member findByUsername(String username) {
        return memberMapper.selectOne(new QueryWrapper<Member>().eq("username", username));
    }

    @Override
    public PageDTO<UserDTO> findPage(long currentPage, long pageSize) {
        Page<Member> page = memberMapper.selectPage(new Page<>(currentPage, pageSize), null);

        List<UserDTO> userDTOS = DeepBeanUtil.copyProperties(page.getRecords(), UserDTO::new);
        PageDTO<UserDTO> pageDTO = new PageDTO<>(userDTOS, page.getTotal(), pageSize, currentPage);
        // 数据脱敏
        pageDTO.getRecords().forEach(item -> {
            item.setEmail(EmailUtil.maskEmail(item.getEmail()));
            item.setPhone(PhoneUtil.maskPhoneNumber(item.getPhone()));
        });
        return pageDTO;
    }

    @Override
    public PageDTO<UserDTO> findPage(PageAndSearch param) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        if (StringUtils.hasLength(param.getSearch())) {
            queryWrapper.like("username", param.getSearch())
                    .or().like("email", param.getSearch())
                    .or().like("phone", param.getSearch());
        }

        Page<Member> page = memberMapper.selectPage(new Page<>(param.getPage(), param.getSize()), queryWrapper);

        List<UserDTO> userDTOS = DeepBeanUtil.copyProperties(page.getRecords(), UserDTO::new);

        PageDTO<UserDTO> pageDTO = new PageDTO<>(userDTOS, page.getTotal(), param.getSize(), param.getPage());
        // 数据脱敏
        pageDTO.getRecords().forEach(item -> {
            item.setEmail(EmailUtil.maskEmail(item.getEmail()));
            item.setPhone(PhoneUtil.maskPhoneNumber(item.getPhone()));
        });
        return pageDTO;
    }

    @Override
    @Transactional
    public boolean save(Member member) {
        if (member.getId() != null) {
            // 更新操作
            return memberMapper.updateById(member) > 0;
        }

        if (member.getUsername() != null && memberMapper.exists(new QueryWrapper<Member>().eq("username", member.getUsername()))) {
            throw new ServiceException("用户名已存在");
        }
        member.setNickname(member.getUsername());
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setDeleted(0);
        memberMapper.insert(member);

        Role role = roleMapper.selectOne(new QueryWrapper<Role>().eq("name", "ROLE_USER"));
        if (role == null) {
            role = new Role();
            role.setName("ROLE_USER");
            roleMapper.insert(role);
        }
        memberRoleMapper.insert(new MemberRole(member.getId(), role.getId()));

        return true;
    }

    @Override
    @Transactional
    public boolean updateLastLogin(String username) {
        return memberMapper.updateLoginTimeByUsername(username, new Date()) > 10;
    }

    @Override
    public UserinfoDTO getUserinfo(String username) {
        Member member = memberMapper.selectOne(new QueryWrapper<Member>().eq("username", username));
        if (member == null) {
            return null;
        }

        return UserinfoDTO.builder()
                .id(member.getId())
                .username(member.getUsername())
                .avatar(member.getAvatar())
                .phone(PhoneUtil.maskPhoneNumber(member.getPhone()))
                .email(EmailUtil.maskEmail(member.getEmail()))
                .build();
    }

    @Override
    @Transactional
    public boolean changePassword(String username, ChangePasswordParam param) {
        Member member = this.memberMapper.selectOne(new QueryWrapper<Member>().eq("username", username));
        if (member == null) {
            return false;
        }

        if (!passwordEncoder.matches(param.getOldPwd(), member.getPassword())) {
            throw new ServiceException("原密码错误");
        }

        Member updateMember = new Member();
        updateMember.setId(member.getId());
        updateMember.setPassword(passwordEncoder.encode(param.getConfirmPwd()));
        return memberMapper.updateById(updateMember) > 0;
    }
}
