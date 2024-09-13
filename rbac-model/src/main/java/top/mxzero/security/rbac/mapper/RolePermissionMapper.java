package top.mxzero.security.rbac.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.mxzero.security.rbac.entity.RolePermission;

import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/21
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    int insertBatch(List<RolePermission> rolePermissions);
}
