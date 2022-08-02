package 并发编程14.限流组件.config;

import io.netty.util.internal.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import 并发编程14.限流组件.annotation.RequestLimit;
import 并发编程14.限流组件.redis.IRedisService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * 请求限流组件
 *
 * @Author idea
 * @Date created in 3:48 下午 2022/7/24
 */
public class RequestLimitInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestLimitInterceptor.class);

    private IRedisService redisService;

    public RequestLimitInterceptor(IRedisService redisService) {
        this.redisService = redisService;
    }

    private static String luaScript = "local key = KEYS[1]\n" +
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

    public static void main(String[] args) {
        System.out.println(luaScript);
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            RequestLimit requestLimit = hm.getMethodAnnotation(RequestLimit.class);
            if (requestLimit == null) {
                return true;
            }
            Long current = (Long) redisService.execLua(luaScript, Arrays.asList("limit-key-" + requestLimit.name()), Arrays.asList(String.valueOf(
                    requestLimit.seconds()),
                    String.valueOf(requestLimit.limit())));
            if (current == null || current > requestLimit.limit()) {
                LOGGER.error("已经达到限流设置了,name is " +
                        "{}, limit is {}, time gap is {}, current is {}", requestLimit.name(), requestLimit.limit(), requestLimit.seconds(),current);
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }

}
