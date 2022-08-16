package 并发编程15.分库分表.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import 并发编程15.分库分表.service.IUserMessageService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author idea
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

    @GetMapping(value = "/selectNotReply")
    public void selectNotReply(){
        Set<Long> idSet = new HashSet<>();
        idSet.add(1L);
        idSet.add(2L);
        idSet.add(3L);
        userMessageService.selectNotReply(idSet);
    }
}
