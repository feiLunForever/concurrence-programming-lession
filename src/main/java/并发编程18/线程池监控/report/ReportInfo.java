package 并发编程18.线程池监控.report;

/**
 * @Author linhao
 * @Date created in 8:58 上午 2022/8/4
 */
public class ReportInfo {

    /**
     * 任务id
     */
    private String jobId;

    /**
     * 任务标签
     */
    private String tag;

    /**
     * 执行时间 毫秒
     */
    private long executeTime;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    @Override
    public String toString() {
        return "ReportInfo{" +
                "jobId='" + jobId + '\'' +
                ", tag='" + tag + '\'' +
                ", executeTime=" + executeTime +
                '}';
    }
}
