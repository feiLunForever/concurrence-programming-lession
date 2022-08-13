package 并发编程18.线程池监控.report;

import 并发编程18.线程池监控.executor.ThreadPoolDetailInfo;

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


    /**
     * 上报线程池的信息
     *
     * @param threadPoolDetailInfo
     */
    void doReportThreadPoolInfo(ThreadPoolDetailInfo threadPoolDetailInfo);
}
