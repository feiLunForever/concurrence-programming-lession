package 并发编程18.线程池监控.report;


import 并发编程18.线程池监控.executor.ThreadPoolDetailInfo;
import 并发编程18.线程池监控.report.redis.IRedisService;

/**
 * 往redis异步上报数据信息
 *
 * @Author linhao
 * @Date created in 9:04 上午 2022/8/4
 */
public class RedisAsyncReporter implements IReporter{

    private IRedisService redisService;
    private static final String JOB_INFO_LIST = "job_info_list";
    private static final String THREAD_POOL_DETAIL_INFO_LIST = "thread_pool:detail_info_list";

    @Override
    public void doReport(ReportInfo reportInfo) {
        redisService.lpush(JOB_INFO_LIST,reportInfo.toJson());
    }

    @Override
    public void doReportThreadPoolInfo(ThreadPoolDetailInfo threadPoolDetailInfo) {
        redisService.lpush(THREAD_POOL_DETAIL_INFO_LIST,threadPoolDetailInfo.toJson());
    }


}
