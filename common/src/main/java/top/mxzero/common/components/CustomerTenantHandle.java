package top.mxzero.common.components;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import top.mxzero.common.utils.TenantUtils;

import java.util.Set;

/**
 * @author Peng
 * @since 2024/9/5
 */
public class CustomerTenantHandle implements TenantLineHandler {
    private static final Set<String> IGNORE_TABLE_LIST = Set.of("t_user", "t_role", "t_permission", "t_user_role", "t_role_permission");

    @Override
    public Expression getTenantId() {
        return new LongValue(TenantUtils.getTenantId());
    }

    @Override
    public boolean ignoreTable(String tableName) {
        return IGNORE_TABLE_LIST.contains(tableName);
    }
}
