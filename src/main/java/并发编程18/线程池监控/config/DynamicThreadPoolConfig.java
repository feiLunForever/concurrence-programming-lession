package 并发编程18.线程池监控.config;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import 并发编程18.线程池监控.executor.IExecutor;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author linhao
 * @Date created in 5:41 下午 2022/8/4
 */
@Configuration
@EnableConfigurationProperties(value = DynamicThreadPoolProperties.class)
public class DynamicThreadPoolConfig implements InitializingBean {

    @Resource
    private DynamicThreadPoolProperties dynamicThreadPoolProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String, IExecutorProperties> executorMap = dynamicThreadPoolProperties.getExecutors();
        for (String executorName : executorMap.keySet()) {
            IExecutorProperties executorProperties = executorMap.get(executorName);
            IExecutor executor = new IExecutor(
                    Integer.valueOf(executorProperties.getCorePoolSize()),
                    Integer.valueOf(executorProperties.getMaximumPoolSize()),
                    Long.valueOf(executorProperties.getKeepAliveTime()),
                    TimeUnit.MILLISECONDS,
                    executorName,
                    executorProperties.getThreadPoolName(),
                    new ArrayBlockingQueue<>(100),
                    new ThreadPoolExecutor.AbortPolicy(),
                    Boolean.valueOf(executorProperties.getAllowsCoreThreadTimeOut()),
                    Boolean.valueOf(executorProperties.getPreStartCoreThread()),
                    Boolean.valueOf(executorProperties.getPreStartAllCoreThreads())
                    );
        }
        System.out.println(dynamicThreadPoolProperties);
    }
}
