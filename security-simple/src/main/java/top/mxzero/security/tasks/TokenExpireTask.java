package top.mxzero.security.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.mxzero.security.entity.UserToken;
import top.mxzero.security.mapper.UserTokenMapper;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/20
 */
@Slf4j
@Component
public class TokenExpireTask {
    @Autowired
    private UserTokenMapper tokenMapper;

    /**
     * token过期任务
     */
    @Scheduled(fixedRate = 10000)
    public void tokenExpireHandle() {
        int count = tokenMapper.updateExpiredTokensBatch(UserToken.TokenState.EXPIRE.getState(), UserToken.TokenState.NORMAL.getState());
        if (count > 0) {
            log.info("已过期{}条token", count);
        }
    }
}
