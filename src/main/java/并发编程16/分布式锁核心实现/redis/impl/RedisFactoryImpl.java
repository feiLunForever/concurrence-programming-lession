package 并发编程16.分布式锁核心实现.redis.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import 并发编程16.分布式锁核心实现.redis.IRedisFactory;
import 并发编程16.分布式锁核心实现.config.RedisProperties;


/**
 * 适用于单机版数据库 根据选用不同的库来区分不同的数据环境
 *
 * @author idea
 * @data 2020/4/1
 */
public class RedisFactoryImpl implements IRedisFactory {

    private static final Logger log = LoggerFactory.getLogger(RedisFactoryImpl.class);

    private static JedisPool jedisPool;

    private static boolean hasInitJedisPool = false;

    private RedisProperties redisProperties;

    public RedisFactoryImpl(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
        buildJedisPool(redisProperties);
    }

    @Override
    public JedisPool buildJedisPool(RedisProperties redisProperties) {
        if (jedisPool != null) {
            log.info("jedisPoolConfig has been created.");
            return jedisPool;
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(redisProperties.getMaxIdle());
        jedisPoolConfig.setMinIdle(redisProperties.getMinIdle());
        jedisPoolConfig.setMaxWaitMillis(redisProperties.getMaxWaitMillis());
        hasInitJedisPool = true;
        jedisPool = new JedisPool(jedisPoolConfig, redisProperties.getHost(), redisProperties.getPort(), redisProperties.getTimeOut(), redisProperties.getPassword(), redisProperties.getIndex());
        return jedisPool;
    }

    @Override
    public Jedis getConnection() {
        try {
            if (hasInitJedisPool) {
                return jedisPool.getResource();
            }
        } catch (Exception e) {
            log.error("jedis create was fail , error is full");
        }
        return null;
    }

    @Override
    public Boolean close(Jedis jedis) {
        try {
            jedis.close();
            return true;
        } catch (Exception e) {
            log.error("jedis close was fail , error is ", e);
        }
        return false;
    }

}
