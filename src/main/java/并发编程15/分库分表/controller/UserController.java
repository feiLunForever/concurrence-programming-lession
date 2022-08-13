package 并发编程15.分库分表.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import 并发编程15.分库分表.service.IUserMessageService;

import javax.annotation.Resource;

/**
 * @Author linhao
 * @Date created in 10:55 下午 2022/8/9
 */
@RestController
public class UserController {

    @Resource
    private IUserMessageService userMessageService;


    @GetMapping(value = "/doQueryFromSplitTable")
    public void doQueryFromSplitTable(Long userId){
        userMessageService.doQueryFromSplitTable(userId);
    }
}
