package top.mxzero.security.rbac.component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Peng
 * @since 2024/9/24
 */
@Slf4j
@RequiredArgsConstructor
public class RbacDataInit implements CommandLineRunner {
    private static final String SYSTEM_TABLE_NAME = "system_status";

    private final JdbcTemplate jdbcTemplate;

    private final PlatformTransactionManager transactionManager;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED); // 设置隔离级别
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED); // 设置传播行为

        TransactionStatus status = transactionManager.getTransaction(def);

        try {


            // 1. 检查 system_status 表是否存在
            if (!isTableExist(SYSTEM_TABLE_NAME)) {
                // 2. 如果不存在则创建该表
                createSystemStatusTable();
            }

            // 3. 检查 system_status 表中 data_initialized 的状态
            String checkStatusSql = "SELECT status_value FROM system_status WHERE status_key = 'data_initialized'";
            List<Map<String, Object>> statusList = jdbcTemplate.queryForList(checkStatusSql);

            if (statusList.isEmpty() || !"true".equals(statusList.get(0).get("status_value"))) {
                // 4. 如果数据没有初始化，则执行初始化逻辑
                initializeData();

                // 将 status_key = 'data_initialized' 设置为 true
                String updateStatusSql = "INSERT INTO system_status (status_key, status_value) VALUES ('data_initialized', 'true') ON CONFLICT (status_key) DO UPDATE SET status_value = EXCLUDED.status_value";
                jdbcTemplate.update(updateStatusSql);
            } else {
                log.info("Data has already been initialized. Skipping initialization.");
            }

            transactionManager.commit(status);
        } catch (Exception e) {
            log.error(e.getMessage());
            transactionManager.rollback(status);
        }
    }

    // 检查表是否存在的方法（Postgresql 版本）
    private boolean isTableExist(String tableName) {
        String checkTableExistsSql = "SELECT to_regclass('public." + tableName + "')";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(checkTableExistsSql);
        return result.get(0).get("to_regclass") != null;
    }

    // 创建 system_status 表的方法
    private void createSystemStatusTable() {
        String createTableSql = "CREATE TABLE system_status ("
                + "status_key VARCHAR(255) NOT NULL, "
                + "status_value VARCHAR(255) NOT NULL, "
                + "PRIMARY KEY (status_key))";
        jdbcTemplate.execute(createTableSql);
        log.info("Table 'system_status' created successfully.");
    }

    // 数据初始化的方法
    private void initializeData() {
        jdbcTemplate.update("INSERT INTO t_user (id, username, password, nickname, created_time) VALUES (1, 'admin', ?, 'admin', ?)", passwordEncoder.encode("admin"), new Date());

        jdbcTemplate.execute("INSERT INTO t_role (id, name) VALUES (1, 'ROLE_ADMIN')");

        jdbcTemplate.execute("INSERT INTO t_user_role (id, user_id, role_id) VALUES (1, 1, 1)");

        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (1, 'role:add')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (2, 'role:remove')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (3, 'role:update')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (4, 'role:list')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (5, 'role:get')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (6, 'permission:remove')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (7, 'permission:update')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (8, 'permission:list')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (9, 'permission:add')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (10, 'permission:get')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (11, 'user:add')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (12, 'user:remove')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (13, 'user:update')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (14, 'user:list')");
        jdbcTemplate.execute("INSERT INTO t_permission (id, name) VALUES (15, 'user:get')");

        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (1, 1, 1)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (2, 1, 2)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (3, 1, 3)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (4, 1, 4)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (5, 1, 5)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (6, 1, 6)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (7, 1, 7)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (8, 1, 8)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (9, 1, 9)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (10, 1, 10)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (11, 1, 11)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (12, 1, 12)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (13, 1, 13)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (14, 1, 14)");
        jdbcTemplate.execute("INSERT INTO t_role_permission (id, role_id, permission_id) VALUES (15, 1, 15)");

        log.info("Data initialized successfully.");
    }
}
