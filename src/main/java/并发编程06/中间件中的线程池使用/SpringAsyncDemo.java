package 并发编程06.中间件中的线程池使用;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @Author idea
 * @Date created in 10:44 下午 2022/6/19
 */
@SpringBootApplication
@EnableAsync
public class SpringAsyncDemo {

    public static void main(String[] args) {
        SpringApplication.run(SpringAsyncDemo.class);
    }
}
