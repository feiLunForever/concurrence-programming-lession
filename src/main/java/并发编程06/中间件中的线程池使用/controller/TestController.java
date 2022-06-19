package 并发编程06.中间件中的线程池使用.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import 并发编程06.中间件中的线程池使用.service.AsyncService;

import javax.annotation.Resource;

/**
 * @Author linhao
 * @Date created in 11:00 下午 2022/6/19
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Resource
    private AsyncService asyncService;

    @GetMapping(value = "/do-test")
    public String doTest(){
        asyncService.testAsync();
        return "success";
    }
}
