package top.mxzero.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.mxzero.security.entity.UserToken;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/19
 */
@Mapper
public interface UserTokenMapper extends BaseMapper<UserToken> {

    int updateExpiredTokensBatch(@Param("newState") int newState, @Param("currentState") int currentState);
}
