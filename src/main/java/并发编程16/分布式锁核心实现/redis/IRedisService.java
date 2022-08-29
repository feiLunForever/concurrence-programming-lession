package 并发编程16.分布式锁核心实现.redis;


import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author idea
 * @data 2020/4/1
 */
public interface IRedisService {

    /**
     * 获取字符串类型value
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 获取数字类型value
     *
     * @param key
     * @return
     */
    Integer getInt(String key);

    /**
     * @param key
     * @return >0存活时长 -2不存在 -1没有时长限制
     */
    Long ttl(String key);

    /**
     * 给键值延长时长
     *
     * @param key
     * @return
     */
    Boolean expire(String key, int ttl, TimeUnit timeUnit);

    /**
     * 删除key
     *
     * @param key
     * @return
     */
    Boolean del(String key);

    /**
     * 执行lua脚本内容
     *
     * @param luaScript
     * @param keys
     * @param args
     * @return
     */
    Object execLua(String luaScript, List<String> keys, List<String> args);

    /**
     * 默认减少1
     *
     * @param key
     * @return
     */
    Long decr(String key);

    /**
     * 设置
     *
     * @param key
     * @param value
     * @param expireSecond
     * @return
     */
    Boolean setNx(String key, Object value, int expireSecond);


}
