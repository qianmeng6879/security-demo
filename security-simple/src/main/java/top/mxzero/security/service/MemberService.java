package top.mxzero.security.service;

import top.mxzero.security.controller.params.ChangePasswordParam;
import top.mxzero.security.controller.params.PageAndSearch;
import top.mxzero.security.dto.PageDTO;
import top.mxzero.security.dto.UserDTO;
import top.mxzero.security.dto.UserinfoDTO;
import top.mxzero.security.entity.Member;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
public interface MemberService {
    Long getUserIdByUsername(String username);

    Member findByUsername(String username);

    PageDTO<UserDTO> findPage(long currentPage, long pageSize);

    PageDTO<UserDTO> findPage(PageAndSearch param);

    boolean save(Member member);

    boolean updateLastLogin(String username);

    UserinfoDTO getUserinfo(String username);

    boolean changePassword(String username, ChangePasswordParam param);
}
