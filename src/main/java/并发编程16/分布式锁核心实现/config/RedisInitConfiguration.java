package 并发编程16.分布式锁核心实现.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import 并发编程16.分布式锁核心实现.lock.IDistributionLock;
import 并发编程16.分布式锁核心实现.lock.impl.DistributionLock;
import 并发编程16.分布式锁核心实现.redis.IRedisFactory;
import 并发编程16.分布式锁核心实现.redis.IRedisService;
import 并发编程16.分布式锁核心实现.redis.impl.RedisFactoryImpl;
import 并发编程16.分布式锁核心实现.redis.impl.RedisServiceImpl;

import javax.annotation.Resource;

/**
 * @Author linhao
 * @Date created in 6:47 下午 2021/4/30
 */
@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisInitConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisInitConfiguration.class);

    @Resource
    private RedisProperties redisProperties;

    @Bean
    @ConditionalOnMissingBean
    public IRedisFactory iRedisFactory() {
        return new RedisFactoryImpl(redisProperties);
    }

    @Bean
    @ConditionalOnBean(value = IRedisFactory.class)
    public IRedisService iRedisService(@Qualifier("iRedisFactory") IRedisFactory iRedisFactory) {
        LOGGER.info("##### RedisInitConfiguration init iRedisService #####");
        RedisServiceImpl iRedisService = new RedisServiceImpl();
        iRedisService.setiRedisFactory(iRedisFactory);
        return iRedisService;
    }

    @Bean
    @ConditionalOnBean(IRedisService.class)
    public IDistributionLock distributionLock(@Qualifier("iRedisService") IRedisService redisService){
        LOGGER.info("##### RedisInitConfiguration init distributionLock #####");
        return new DistributionLock(redisService);
    }


}
