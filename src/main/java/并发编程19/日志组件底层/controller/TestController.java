package 并发编程19.日志组件底层.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author linhao
 * @Date created in 11:03 上午 2022/9/4
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    private static Logger logger = LoggerFactory.getLogger(TestController.class);

    @GetMapping(value = "log")
    public void log(){
        logger.info("goodInfo's store is {}");
    }


}
