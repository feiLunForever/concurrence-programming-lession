package 并发编程07.接口请求次数统计.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author idea
 * @Date created in 10:39 下午 2022/6/21
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    private int a = 10;
    private int b = 10;
    private int c = 10;

    @GetMapping(value = "/do-count")
    public void reduce() {
        synchronized (this) {
            if(a==0 || b==0 || c==0){
                System.out.println(a + "," + b + "," + c);
                return;
            }
            a--;
            b--;
            c--;
            System.out.println(a + "," + b + "," + c);
        }
    }
}
