package 并发编程06.中间件中的线程池使用.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @Author linhao
 * @Date created in 11:02 下午 2022/6/19
 */
@Service
public class AsyncService {

    @Async
    public void testAsync(){
        try {
            Thread.sleep(1000*2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName()+" async test");
    }
}
