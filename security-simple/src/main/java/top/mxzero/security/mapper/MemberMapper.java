package top.mxzero.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import top.mxzero.security.entity.Member;

import java.util.Date;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {
    @Select("select id from t_member where username = #{username}")
    Long findIdByUsername(String username);

    @Update("update t_member set last_login_time = #{date} where username = #{username} and deleted = 0")
    int updateLoginTimeByUsername(@Param("username") String username, @Param("date") Date date);
}
