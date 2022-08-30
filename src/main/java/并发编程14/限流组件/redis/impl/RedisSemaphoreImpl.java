package 并发编程14.限流组件.redis.impl;

import org.springframework.stereotype.Component;
import 并发编程14.限流组件.redis.IRedisSemaphore;
import 并发编程14.限流组件.redis.IRedisService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

/**
 * @Author idea
 * @Date created in 5:47 下午 2022/7/29
 */
@Component
public class RedisSemaphoreImpl implements IRedisSemaphore {

    @Resource
    private IRedisService redisService;

    private static final String SEMAPHORE_SCRIPT =
            "local key = KEYS[1]\n" +
            "local expire = tonumber(ARGV[1])\n" +
            "local count = tonumber(ARGV[2])\n" +
            "\n" +
            "local current = redis.call('get',key)\n" +
            "\n" +
            "if current and tonumber(current) > count then\n" +
            "   return tonumber(current);\n" +
            "end\n" +
            "\n" +
            "current = redis.call('incr',key)\n" +
            "local ttl = redis.call('ttl',key)\n" +
            "\n" +
            "if tonumber(current) ==1 then \n" +
            "   redis.call('expire',key,expire)\n" +
            "else \n" +
            "   if ttl and tonumber(ttl) == -1 then\n" +
            "      redis.call('expire',key,expire)\n" +
            "   end\n" +
            "end\n" +
            "\n" +
            "return tonumber(current)\n" +
            "\n" +
            "\n";


    @Override
    public boolean tryAcquire(String name,int limit,int expireTime) {
        redisService.execLua(SEMAPHORE_SCRIPT, Arrays.asList("semaphore-"+name),Arrays.asList(String.valueOf(
                expireTime),
                String.valueOf(limit)));
        return false;
    }

    @Override
    public boolean release() {
        return false;
    }


}
