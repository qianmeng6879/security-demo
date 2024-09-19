package top.mxzero.endpoint.utils;

import org.apache.ibatis.cache.Cache;

/**
 * @author Peng
 * @since 2024/9/19
 */
public class MybatisRedisCache implements Cache {
    private final String id;

    private MybatisRedisCache(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return "";
    }

    @Override
    public void putObject(Object key, Object value) {

    }

    @Override
    public Object getObject(Object key) {
        return null;
    }

    @Override
    public Object removeObject(Object key) {
        return null;
    }

    @Override
    public void clear() {

    }

    @Override
    public int getSize() {
        return 0;
    }
}
