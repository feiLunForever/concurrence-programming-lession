package 并发编程14.限流组件.redis.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import 并发编程14.限流组件.redis.IRedisService;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

/**
 * @Author linhao
 * @Date created in 6:06 下午 2022/7/29
 */
@Component
public class RedisCountDownLatch {

    @Autowired
    private IRedisService redisService;

    private static final String countDownScript =
            "local key = KEYS[1]\n" +
                    "local limit = KEYS[2]\n" +
                    "current = redis.call('get',key)\n" +
                    "if current and tonumber(current) == 0 then"+
                    "   return 0\n"+
                    "current = redis.call('decr',key)" +
                    "return tonumber(current)";

    public static final String awaitScript =
            "local key = KEYS[1]\n" +
                    "local current = redis.call('get',key)\n" +
                    "if current and tonumber(current) == 0 then\n"+
                    "return 0\n"+
                    "else\n"+
                    "return 1 end";

    public boolean countDown(String name, int count) {
        redisService.decr("redis-countdown-latch-" + name);
        Long current = (Long) redisService.execLua(countDownScript, Arrays.asList("name", "limit"), Arrays.asList(name, String.valueOf(count)));
        if (current > 0) {
            return true;
        } else {
            return false;
        }
    }



    public boolean await(String name) {
        redisService.execLua(awaitScript,Arrays.asList("name"),Arrays.asList(name));
        return true;
    }
}
