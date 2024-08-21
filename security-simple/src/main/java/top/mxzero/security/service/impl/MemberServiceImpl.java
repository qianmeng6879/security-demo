package top.mxzero.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    public Member findByUsername(String username) {
        return memberMapper.selectOne(new QueryWrapper<Member>().eq("username", username));
    }

    @Override
    public List<Member> findPage(long currentPage, long pageSize) {
        return memberMapper.selectPage(new Page<>(currentPage, pageSize), null).getRecords();
    }

    @Override
    @Transactional
    public boolean save(Member member) {
        if (memberMapper.exists(new QueryWrapper<Member>().eq("username", member.getUsername()))) {
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
}
