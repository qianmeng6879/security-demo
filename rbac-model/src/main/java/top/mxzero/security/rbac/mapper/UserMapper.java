package top.mxzero.security.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.mxzero.security.rbac.entity.User;

import java.util.Date;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
    @Select("select id,username,password,nickname,email,phone,created_time,updated_time,last_login_time,avatar_url from t_user where username = #{username} and deleted = 0")
    User findByUsername(String username);

    @Update("update t_user set last_login_time = #{date} where id = #{id} and deleted = 0")
    int updateLoginTimeById(@Param("id") Long id, @Param("date") Date date);
}