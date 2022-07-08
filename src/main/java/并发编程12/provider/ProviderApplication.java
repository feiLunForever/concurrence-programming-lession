package 并发编程12.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author linhao
 * @Date created in 7:36 下午 2022/7/5
 */
@SpringBootApplication
@EnableDubbo
public class ProviderApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(ProviderApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
    }
}
