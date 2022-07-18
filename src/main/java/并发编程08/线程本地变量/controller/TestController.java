package 并发编程08.线程本地变量.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author idea
 * @Date created in 3:20 下午 2022/6/26
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping(value = "/do-test")
    public String doTest() throws InterruptedException {
        System.out.println("start do test");
        Thread.sleep(2000);
        System.out.println("end do test");
        return "success";
    }
}
