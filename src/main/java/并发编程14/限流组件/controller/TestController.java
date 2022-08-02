package 并发编程14.限流组件.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import 并发编程14.限流组件.annotation.RequestLimit;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author linhao
 * @Date created in 3:37 下午 2022/7/24
 */
@RestController
public class TestController {


    @RequestLimit(seconds = 1, limit = 10, name = "testRequestLimit")
    @GetMapping(value = "/do-test")
    public String doTest() throws InterruptedException {
        System.out.println("test");
        Thread.sleep(300);
        System.out.println("end");
        return "success";
    }

}
