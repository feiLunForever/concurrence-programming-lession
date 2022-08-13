package 并发编程18.线程池监控.executor;

import com.alibaba.fastjson.JSON;

/**
 * @Author linhao
 * @Date created in 5:30 下午 2022/8/4
 */
public class ThreadPoolDetailInfo {

    private Integer corePoolSize;

    private Integer maxPoolSize;

    private Integer queueSize;

    private Integer activeQueueSize;

    private Long complexTaskCount;

    private Long errorTaskNum;

    public Integer getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public Integer getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(Integer maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public Integer getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(Integer queueSize) {
        this.queueSize = queueSize;
    }

    public Integer getActiveQueueSize() {
        return activeQueueSize;
    }

    public void setActiveQueueSize(Integer activeQueueSize) {
        this.activeQueueSize = activeQueueSize;
    }

    public Long getComplexTaskCount() {
        return complexTaskCount;
    }

    public void setComplexTaskCount(Long complexTaskCount) {
        this.complexTaskCount = complexTaskCount;
    }

    public Long getErrorTaskNum() {
        return errorTaskNum;
    }

    public void setErrorTaskNum(Long errorTaskNum) {
        this.errorTaskNum = errorTaskNum;
    }

    @Override
    public String toString() {
        return "ThreadPoolDetailInfo{" +
                "corePoolSize=" + corePoolSize +
                ", maxPoolSize=" + maxPoolSize +
                ", queueSize=" + queueSize +
                ", activeQueueSize=" + activeQueueSize +
                ", complexTaskNum=" + complexTaskCount +
                ", errorTaskNum=" + errorTaskNum +
                '}';
    }

    public String toJson(){
        return JSON.toJSONString(this);
    }
}
