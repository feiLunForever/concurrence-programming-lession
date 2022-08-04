package 并发编程18.线程池监控.report;

/**
 * @Author linhao
 * @Date created in 8:58 上午 2022/8/4
 */
public interface IReporter {

    /**
     * 上报线程池数据
     *
     * @param reportInfo
     */
    void doReport(ReportInfo reportInfo);
}
