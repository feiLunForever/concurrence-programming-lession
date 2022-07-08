package 并发编程12.consumer.controller;

import dubbo.interfaces.UserRpcService;
import org.apache.dubbo.config.annotation.DubboReference;
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

    @DubboReference
    private UserRpcService userRpcService;

    @GetMapping(value = "/do-trace")
    public String doIdTrace(){
        userRpcService.isUserExist(1001);
        return "success";
    }
}
