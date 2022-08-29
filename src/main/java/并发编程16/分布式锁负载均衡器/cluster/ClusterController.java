package 并发编程16.分布式锁负载均衡器.cluster;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import 并发编程16.分布式锁负载均衡器.utils.HttpUtil;

/**
 * 负载均衡器
 *
 * @Author idea
 * @Date created in 8:48 上午 2022/8/28
 */
@RequestMapping(value = "/cluster")
@RestController
public class ClusterController {

    private static final String NOT_LOCK_DECR_URL_1 = "http://localhost:8081/test/doDecrStoreNotWithLock";
    private static final String NOT_LOCK_DECR_URL_2 = "http://localhost:8082/test/doDecrStoreNotWithLock";
    private static final String LOCK_DECR_URL_1 = "http://localhost:8081/test/doDecrStoreWithLock";
    private static final String LOCK_DECR_URL_2 = "http://localhost:8082/test/doDecrStoreWithLock";


    /**
     * 模拟负载均衡请求
     *
     * @return
     */
    @GetMapping(value = "/decr")
    public Object decr(boolean lockState) {
        int result = RandomUtils.nextInt(0, 2);
        if (result == 1) {
            if (lockState) {
                HttpUtil.doGet(LOCK_DECR_URL_1);
            } else {
                HttpUtil.doGet(NOT_LOCK_DECR_URL_1);
            }
        } else {
            if (lockState) {
                HttpUtil.doGet(LOCK_DECR_URL_2);
            } else {
                HttpUtil.doGet(NOT_LOCK_DECR_URL_2);
            }
        }
        return null;
    }

}
