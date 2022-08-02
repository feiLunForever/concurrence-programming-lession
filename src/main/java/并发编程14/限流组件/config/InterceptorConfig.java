package 并发编程14.限流组件.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import 并发编程14.限流组件.redis.IRedisService;

import java.util.concurrent.Semaphore;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private IRedisService redisService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestLimitInterceptor(redisService))
                .addPathPatterns("/**");//拦截所有的路径
    }
}