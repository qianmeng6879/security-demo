package top.mxzero.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mxzero.security.entity.Role;

import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/7/30
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {
    List<String> findNameByMemberId(Long memberId);

}
