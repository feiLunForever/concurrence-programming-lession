package 并发编程19.日志组件底层;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 通过调整application.properties文件中的logging.config参数来定义
 * 要使用什么样的模式（同步/异步）去实现日志记录
 * @Author idea
 * @Date created in 11:02 上午 2022/9/4
 */
@SpringBootApplication
public class LogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogApplication.class);
    }
}
