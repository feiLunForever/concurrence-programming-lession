package 并发编程18.线程池监控.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import 并发编程18.线程池监控.report.IReporter;
import 并发编程18.线程池监控.report.ReportInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * 线程池后台监视者
 *
 * @Author linhao
 * @Date created in 12:29 下午 2022/8/4
 */
public class IThreadPoolWatcher implements Runnable{

    private static final Logger LOGGER = LoggerFactory.getLogger(IThreadPoolWatcher.class);

    private Map<String,IExecutor> executorMap;

    private IReporter reporter;

    public IThreadPoolWatcher(Map<String, IExecutor> executorMap, IReporter reporter) {
        this.executorMap = executorMap;
        this.reporter = reporter;
    }

    @Override
    public void run() {
        while (true) {
            try {
                for (String executorName : executorMap.keySet()) {
                    IExecutor executor = executorMap.get(executorName);
                    ThreadPoolDetailInfo threadPoolDetailInfo = new ThreadPoolDetailInfo();
                    threadPoolDetailInfo.setActiveQueueSize(executor.getActiveCount());
                    threadPoolDetailInfo.setComplexTaskCount(executor.getCompletedTaskCount());
                    threadPoolDetailInfo.setMaxPoolSize(executor.getMaximumPoolSize());
                    threadPoolDetailInfo.setErrorTaskNum(executor.getErrorNum());
                    threadPoolDetailInfo.setQueueSize(1);
                    reporter.doReportThreadPoolInfo(threadPoolDetailInfo);
                }
            } catch (Exception e){
                LOGGER.error("[IThreadPoolWatcher] error is ",e);
            }
        }
    }


}
