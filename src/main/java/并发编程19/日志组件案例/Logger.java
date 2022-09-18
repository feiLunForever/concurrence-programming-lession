package 并发编程19.日志组件案例;

import org.slf4j.LoggerFactory;

/**
 * @Author linhao
 * @Date created in 10:13 上午 2022/9/4
 */
public class Logger {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);

    public void info(String content, Object... param) {
        logger.info("测试代码内容 {}", "参数信息");
        System.out.println(content);
    }

    public void error(String content, Object... param) {
        System.err.println(content);
    }

    public void debug(String content, Object... param) {
        System.out.println(content);
    }
}
