package 并发编程17.手动实现本地缓存.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import 并发编程17.手动实现本地缓存.listener.RefreshListener;

import java.util.concurrent.TimeUnit;

/**
 * 定时刷新本地缓存线程
 *
 * @Author idea
 * @Date created in 10:36 上午 2022/8/30
 */
public class RefreshThread implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(RefreshThread.class);

    private RefreshListener refreshListener;
    private int time;


    public RefreshThread(RefreshListener refreshListener, int time) {
        this.refreshListener = refreshListener;
        this.time = time;
    }

    @Override
    public void run() {
        while (true) {
            try {
                //回调刷新数据的函数
                refreshListener.doRefresh();
                logger.debug("[RefreshThread] refresh success");
                TimeUnit.SECONDS.sleep(time);
            } catch (Exception e) {
                logger.error("[RefreshThread] error is ", e);
            }
        }
    }
}
