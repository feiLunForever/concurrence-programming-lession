package 并发编程15.分库分表.controller;

import com.google.common.collect.Sets;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import 并发编程15.分库分表.po.UserMessagePO;
import 并发编程15.分库分表.service.IUserMessageService;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author idea
 * @Date created in 10:55 下午 2022/8/9
 */
@RestController
public class UserController {

    @Resource
    private IUserMessageService userMessageService;


    @GetMapping(value = "/selectByUserId")
    public List<UserMessagePO> selectByUserId(Long userId) {
        return userMessageService.selectByUserId(userId);
    }

    @GetMapping(value = "/selectNotRead")
    public List<UserMessagePO> selectNotRead() {
        Set<Long> idSet = new HashSet<>();
        idSet.add(121767119L);
        idSet.add(171105119L);
        idSet.add(104005011L);
        return userMessageService.selectNotRead(idSet);
    }


    @GetMapping(value = "/insertData")
    public void insertData() {
        userMessageService.insertData();
    }

    @GetMapping(value = "/splitFromSourceTable")
    public void splitFromSourceTable() {
        userMessageService.splitFromSourceTable();
    }
}
