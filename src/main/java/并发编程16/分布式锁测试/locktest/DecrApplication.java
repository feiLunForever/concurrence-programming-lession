package 并发编程16.分布式锁测试.locktest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import 并发编程16.分布式锁核心实现.config.RedisInitConfiguration;

/**
 * @Author idea
 * @Date created in 10:28 下午 2022/8/24
 */
@SpringBootApplication
@Import(RedisInitConfiguration.class)
public class DecrApplication {

    public static void main(String[] args) {
        SpringApplication.run(DecrApplication.class);
    }
}
