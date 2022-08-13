package 并发编程18.线程池监控.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/**
 * @Author linhao
 * @Date created in 5:44 下午 2022/8/4
 */
@ConfigurationProperties(prefix = "dynamic.idea.threadpool")
public class DynamicThreadPoolProperties   {

    private List<Integer> alarmWorkerId;

    private Map<String,IExecutorProperties> executors;

    public Map<String, IExecutorProperties> getExecutors() {
        return executors;
    }

    public void setExecutors(Map<String, IExecutorProperties> executors) {
        this.executors = executors;
    }

    public List<Integer> getAlarmWorkerId() {
        return alarmWorkerId;
    }

    public void setAlarmWorkerId(List<Integer> alarmWorkerId) {
        this.alarmWorkerId = alarmWorkerId;
    }

    @Override
    public String toString() {
        return "DynamicThreadPoolProperties{" +
                "alarmWorkerId=" + alarmWorkerId +
                ", executors=" + executors +
                '}';
    }



}
