package 并发编程16.分布式锁核心实现.redis.impl;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;
import redis.clients.jedis.params.SetParams;
import 并发编程16.分布式锁核心实现.redis.IRedisFactory;
import 并发编程16.分布式锁核心实现.redis.IRedisService;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author idea
 * @data 2020/4/1
 */
public class RedisServiceImpl implements IRedisService {

    private static Logger log = LoggerFactory.getLogger(RedisServiceImpl.class);

    private IRedisFactory iRedisFactory;

    public void setiRedisFactory(IRedisFactory iRedisFactory) {
        this.iRedisFactory = iRedisFactory;
    }

    @Override
    public String get(String key) {
        try (Jedis jedis = iRedisFactory.getConnection()) {
            return jedis.get(key);
        } catch (Exception e) {
            log.error("jedis get has error, error is ", e);
        }
        return null;
    }


    @Override
    public Integer getInt(String key) {
        try (Jedis jedis = iRedisFactory.getConnection()) {
            return Integer.valueOf(jedis.get(key));
        } catch (Exception e) {
            log.error("jedis setStr has error, error is ", e);
        }
        return null;
    }

    @Override
    public Long ttl(String key) {
        try (Jedis jedis = iRedisFactory.getConnection()) {
            return jedis.ttl(key);
        } catch (Exception e) {
            log.error("jedis ttl has error, error is ", e);
        }
        return null;
    }

    @Override
    public Boolean expire(String key, int ttl, TimeUnit timeUnit) {
        try (Jedis jedis = iRedisFactory.getConnection()) {
            jedis.expire(key, convertToSecond(ttl, timeUnit));
            return true;
        } catch (Exception e) {
            log.error("jedis expire has error, error is ", e);
        }
        return false;
    }

    @Override
    public Boolean del(String key) {
        try (Jedis jedis = iRedisFactory.getConnection()) {
            jedis.del(key);
            return true;
        } catch (Exception e) {
            log.error("jedis del has error, error is ", e);
        }
        return false;
    }

    @Override
    public Object execLua(String luaScript, List<String> keys, List<String> args) {
        try (Jedis jedis = iRedisFactory.getConnection()) {
            Object result = jedis.evalsha(jedis.scriptLoad(luaScript), keys, args);
            return result;
        } catch (Exception e) {
            log.error("jedis execLua has error, error is ", e);
        }
        return null;
    }


    @Override
    public Long decr(String key) {
        try (Jedis jedis = iRedisFactory.getConnection()) {
            return jedis.decr(key);
        } catch (Exception e) {
            log.error("jedis decr has error, error is ", e);
        }
        return null;
    }

    @Override
    public Boolean setNx(String key, Object value, int expireSecond) {
        try (Jedis jedis = iRedisFactory.getConnection()) {
            String result = jedis.set(key, String.valueOf(value), new SetParams().nx().ex(expireSecond));
            return "OK".equals(result);
        } catch (Exception e) {
            log.error("jedis setNx has error, error is ", e);
        }
        return false;
    }


    private static Integer convertToSecond(int ttl, TimeUnit timeUnit) {
        if (ttl < 0) {
            throw new RuntimeException("illegal ttl value");
        }
        switch (timeUnit) {
            case DAYS:
                return ttl * 3600 * 24;
            case HOURS:
                return ttl * 3600;
            case MINUTES:
                return ttl * 60;
            case SECONDS:
                return ttl;
            default:
                throw new RuntimeException("illegal timeUnit");
        }
    }

    private static Integer convertToMillisecond(int ttl, TimeUnit timeUnit) {
        if (ttl < 0) {
            throw new RuntimeException("illegal ttl value");
        }
        switch (timeUnit) {
            case DAYS:
                return ttl * 3600 * 24 * 1000;
            case HOURS:
                return ttl * 3600 * 1000;
            case MINUTES:
                return ttl * 60 * 1000;
            case SECONDS:
                return ttl * 1000;
            case MILLISECONDS:
                return ttl;
            default:
                throw new RuntimeException("illegal timeUnit");
        }
    }


    public static void main(String[] args) {
        TimeUnit[] timeUnits = TimeUnit.values();
    }
}
