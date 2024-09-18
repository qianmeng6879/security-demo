package top.mxzero.security.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mxzero.security.rbac.entity.Permission;

import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> findByRoleId(Long roleId);

    List<String> findNameByUserId(Long userId);
}
