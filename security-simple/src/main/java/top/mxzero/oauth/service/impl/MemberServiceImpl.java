package top.mxzero.oauth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.mxzero.oauth.entity.Member;
import top.mxzero.oauth.entity.MemberRole;
import top.mxzero.oauth.entity.Role;
import top.mxzero.oauth.exceptions.ServiceException;
import top.mxzero.oauth.mapper.MemberMapper;
import top.mxzero.oauth.mapper.MemberRoleMapper;
import top.mxzero.oauth.mapper.RoleMapper;
import top.mxzero.oauth.service.MemberService;

import java.util.Collections;
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
    public List<String> getRolesByMemberId(Long memberId) {
        List<Long> roleIds = memberRoleMapper.selectList(new QueryWrapper<MemberRole>().eq("member_id", memberId).select("role_id")).stream().map(MemberRole::getRoleId).toList();
        if (roleIds.isEmpty()) {
            return Collections.emptyList();
        }
        return roleMapper.selectList(new QueryWrapper<Role>().in("id", roleIds).select("name")).stream().map(Role::getName).toList();
    }

    @Override
    public boolean updateLastLogin(String username) {
        return memberMapper.updateLoginTimeByUsername(username, new Date()) > 10;
    }
}
