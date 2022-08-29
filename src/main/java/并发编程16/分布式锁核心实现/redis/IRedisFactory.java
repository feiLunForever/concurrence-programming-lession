package 并发编程16.分布式锁核心实现.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import 并发编程16.分布式锁核心实现.config.RedisProperties;

/**
 * 只处理jedis的创建，销毁工作
 *
 * @author idea
 * @data 2020/4/1
 */
public interface IRedisFactory {

    /**
     * 构建链接池
     *
     * @return
     */
    JedisPool buildJedisPool(RedisProperties redisProperties);

    /**
     * 获取链接
     *
     * @return
     */
    Jedis getConnection();

    /**
     * 关闭
     *
     * @return
     */
    Boolean close(Jedis jedis);
}
