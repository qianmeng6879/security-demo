package top.mxzero.mp.components;

import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;

/**
 * @author Peng
 * @since 2024/9/5
 */
public class CustomerTenantHandle implements TenantLineHandler {
    @Override
    public Expression getTenantId() {
        return new LongValue(1);
    }

    @Override
    public boolean ignoreTable(String tableName) {
        if ("t_user".equals(tableName)) {
            return true;
        }

        return false;
    }
}
