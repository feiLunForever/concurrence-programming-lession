package 并发编程18.线程池监控.report;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 往redis异步上报数据信息
 *
 * @Author linhao
 * @Date created in 9:04 上午 2022/8/4
 */
public class RedisAsyncReporter implements IReporter{


    @Override
    public void doReport(ReportInfo reportInfo) {
        System.out.println(reportInfo);
    }
}
