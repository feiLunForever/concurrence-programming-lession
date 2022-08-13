package 并发编程18.线程池监控.executor;

import 并发编程18.线程池监控.report.IReporter;
import 并发编程18.线程池监控.report.RedisAsyncReporter;
import 并发编程18.线程池监控.report.ReportInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author linhao
 * @Date created in 8:31 下午 2022/8/3
 */
public class IExecutor extends ThreadPoolExecutor {

    private String poolName;
    private String executorName;

    private AtomicLong errorTaskNum = new AtomicLong();

    private IReporter reporter = new RedisAsyncReporter();

    private final ThreadLocal<Map<String, Object>> taskContent = new ThreadLocal<>();

    public IExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                     String poolName,
                     String executorName,
                     BlockingQueue<Runnable> workQueue,
                     RejectedExecutionHandler handler,
                     Boolean allowCoreThreadTimeOut,
                     Boolean preStartCoreThread,
                     Boolean preStartAllCoreThreads) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, new IThreadFactory(executorName), handler);
        this.poolName = poolName;
        this.executorName = executorName;
        super.allowCoreThreadTimeOut(allowCoreThreadTimeOut);
        if (preStartCoreThread) {
            super.prestartCoreThread();
        }
        if (preStartAllCoreThreads) {
            super.prestartAllCoreThreads();
        }
    }

    @Override
    public void execute(Runnable command) {
        IExecutorJob executorJob = new IExecutorJob("", command);
        super.execute(executorJob);
    }

    public void execute(Runnable command, String tag) {
        IExecutorJob executorJob = new IExecutorJob(tag, command);
        super.execute(executorJob);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        String jobId = UUID.randomUUID().toString();
        Map<String, Object> threadInfoMap = new HashMap<>(4);
        if (r instanceof IExecutorJob) {
            IExecutorJob iExecutorJob = (IExecutorJob) r;
            threadInfoMap.put("tag", iExecutorJob.getTag());
        }
        threadInfoMap.put("jobId", jobId);
        threadInfoMap.put("beginTime", System.currentTimeMillis());
        taskContent.set(threadInfoMap);
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        Map<String, Object> threadInfoMap = taskContent.get();
        long beginTime = (long) threadInfoMap.get("beginTime");
        long executeTime = System.currentTimeMillis() - beginTime;
        String tag = evalString(threadInfoMap.get("tag"));

        ReportInfo reportInfo = new ReportInfo();
        reportInfo.setExecuteTime(executeTime);
        reportInfo.setJobId(String.valueOf(threadInfoMap.get("jobId")));
        reportInfo.setTag(tag);
        reporter.doReport(reportInfo);
    }


    public static String evalString(Object obj) {
        if (obj == null)
            return "";
        return obj.toString();
    }

    public long getErrorNum(){
        return errorTaskNum.get();
    }

    @Override
    public int getCorePoolSize(){
        return super.getCorePoolSize();
    }

    @Override
    public long getTaskCount() {
        return super.getTaskCount();
    }


    @Override
    public long getCompletedTaskCount() {
        return super.getCompletedTaskCount();
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

}
