package top.mxzero.security.service;

import top.mxzero.security.dto.TokenResDTO;
import top.mxzero.security.dto.UserAuthDTO;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/16
 */
public interface TokenService {

    /**
     * 获取token
     *
     * @param dto 用户凭证信息
     * @return 凭证字符串
     */
    TokenResDTO<?> createToken(UserAuthDTO dto);

}
