package top.mxzero.security.tasks;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.mxzero.security.entity.UserToken;
import top.mxzero.security.mapper.UserTokenMapper;

import java.util.List;

/**
 * @author Peng
 * @email qianmeng6879@163.com
 * @since 2024/8/20
 */
@Component
public class TokenExpireTask {
    @Autowired
    private UserTokenMapper tokenMapper;

    private final QueryWrapper<UserToken> queryWrapper = new QueryWrapper<UserToken>()
            .eq("state", UserToken.TokenState.NORMAL.getState());


    private final IPage<UserToken> page = new Page<>(1, 100);

    /**
     * token过期任务
     */
    @Scheduled
    public void tokenExpireHandle() {
        IPage<UserToken> pageData = tokenMapper.selectPage(page, queryWrapper);
        List<UserToken> records = pageData.getRecords();

    }
}
