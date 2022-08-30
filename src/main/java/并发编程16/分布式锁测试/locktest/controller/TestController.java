package 并发编程16.分布式锁测试.locktest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import 并发编程16.分布式锁测试.locktest.service.TestService;

import javax.annotation.Resource;

/**
 * @Author idea
 * @Date created in 10:29 下午 2022/8/24
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Resource
    private TestService testService;

    @GetMapping(value = "/doDecrStoreNotWithLock")
    public void doDecrStoreNotWithLock(){
        testService.doDecrStoreNotWithLock();
    }

    @GetMapping(value = "/doDecrStoreWithLock")
    public void doDecrStoreWithLock(){
        testService.doDecrStoreWithLock();
    }
}
