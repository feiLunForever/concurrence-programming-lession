package 并发编程16.分布式锁负载均衡器;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author idea
 * @Date created in 8:47 上午 2022/8/28
 */
@SpringBootApplication
public class ClusterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClusterApplication.class);
    }
}
