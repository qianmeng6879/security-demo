package top.mxzero.security.service;

import top.mxzero.security.dto.UserinfoDTO;
import top.mxzero.security.entity.Member;

import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
public interface MemberService {
    Member findByUsername(String username);
    List<Member> findPage(long currentPage, long pageSize);
    boolean save(Member member);

    boolean updateLastLogin(String username);
    UserinfoDTO getUserinfo(String username);
}
