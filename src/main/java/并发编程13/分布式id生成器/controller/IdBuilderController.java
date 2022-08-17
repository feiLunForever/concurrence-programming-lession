package 并发编程13.分布式id生成器.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import 并发编程13.分布式id生成器.service.IdBuilderService;


/**
 * @Author linhao
 * @Date created in 4:27 下午 2020/12/17
 */
@RestController
@RequestMapping(value = "id-builder")
public class IdBuilderController {

    @Autowired
    private IdBuilderService idBuilderService;


    @GetMapping("increase-seq-id")
    public Long increaseSeqId(int code){
        long result = idBuilderService.increaseSeqId(code);
        System.out.println(result);
        return result;
    }

    @GetMapping("increase-seq-str-id")
    public String increaseSeqStrId(int code){
        String result = idBuilderService.increaseSeqStrId(code);
        System.out.println(result);
        return result;
    }

}
