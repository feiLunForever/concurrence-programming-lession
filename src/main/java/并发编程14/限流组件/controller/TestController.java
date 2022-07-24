package 并发编程14.限流组件.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import 并发编程14.限流组件.annotation.RequestLimit;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Author linhao
 * @Date created in 3:37 下午 2022/7/24
 */
@RestController
public class TestController {

    private Semaphore semaphore = new Semaphore(1);

    @RequestLimit(timeOut = 1000, limit = 5)
    @GetMapping(value = "/do-test")
    public String doTest() throws InterruptedException {
//        semaphore.tryAcquire(1000, TimeUnit.MILLISECONDS);
        System.out.println("this is begin!");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("this is test");
//        semaphore.release();
        return "success";
    }
}
