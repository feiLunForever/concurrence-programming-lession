package 并发编程12.全链路id生成.consumer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author linhao
 * @Date created in 7:35 下午 2022/7/5
 */
@RestController
@RequestMapping(value = "/id-trace")
public class IdTraceController {

    @GetMapping(value = "/do-trace")
    public String doIdTrace(){
        return "success";
    }
}
