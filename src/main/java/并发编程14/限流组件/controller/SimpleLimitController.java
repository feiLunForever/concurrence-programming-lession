package 并发编程14.限流组件.controller;

import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Semaphore;

/**
 * @Author idea
 * @Date created in 8:46 上午 2022/8/2
 */
@RestController
public class SimpleLimitController {

    private Semaphore semaphore = new Semaphore(10);
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleChannelInboundHandler.class);

    @GetMapping("do-test-limit")
    public void doTest() {
        boolean status = false;
        try {
            //限制流量速度
            status = semaphore.tryAcquire();
            if (status) {
                this.doSomeBiz();
            }
        } catch (Exception e) {
            LOGGER.error("[doTest] error is ", e);
        } finally {
            if (status) {
                semaphore.release();
            }
        }
    }

    /**
     * 执行业务逻辑
     */
    private void doSomeBiz() {
    }
}
