package 并发编程11.paralleStream优化;

import org.apache.catalina.User;
import 并发编程11.用户查询.bean.UserInfoDTO;
import 并发编程11.用户查询.bean.UserInfoPO;
import 并发编程11.用户查询.dao.UserDao;
import 并发编程11.用户查询.service.UserQueryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author idea
 * @Date created in 9:42 下午 2022/7/4
 */
public class ParalleStreamQueryDemo {

    private UserDao userDao = new UserDao();

    private UserQueryService userQueryService = new UserQueryService();

    public ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors()*2);

    public List<UserInfoDTO> batchQueryWithParallelV1(List<Long> userIdList) {
        List<UserInfoDTO> resultList = new ArrayList<>();
        userIdList.parallelStream().forEach(userId -> {
            UserInfoDTO userInfoDTO = userQueryService.queryUserInfoWrapper(userId);
            resultList.add(userInfoDTO);
        });
        return resultList;
    }

    /**
     * 采用了自定义的forkJoinPool
     *
     * @param userIdList
     * @return
     */
    public List<UserInfoDTO> batchQueryWithParallelV2(List<Long> userIdList) {
        Future<List<UserInfoDTO>> resultFuture = forkJoinPool.submit(() -> {
            List<UserInfoDTO> resultList = new ArrayList<>();
            userIdList.parallelStream().forEach(userId -> {
                UserInfoDTO userInfoDTO = userQueryService.queryUserInfoWrapper(userId);
                resultList.add(userInfoDTO);
            });
            return resultList;
        });
        try {
            return resultFuture.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<UserInfoDTO> batchQuery(List<Long> userIdList) {
        List<UserInfoDTO> resultList = new ArrayList<>();
        userIdList.forEach(userId -> {
            UserInfoDTO userInfoDTO = userQueryService.queryUserInfoWrapper(userId);
            resultList.add(userInfoDTO);
        });
        return resultList;
    }

    public static void main(String[] args) {
        List<Long> userIdList = Arrays.asList(1001L, 1002L, 1003L, 1004L, 1005L);
        ParalleStreamQueryDemo paralleStreamQueryDemo = new ParalleStreamQueryDemo();
        long begin = System.currentTimeMillis();
        paralleStreamQueryDemo.batchQueryWithParallelV1(userIdList);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - begin) + "ms");
        long begin2 = System.currentTimeMillis();
        paralleStreamQueryDemo.batchQueryWithParallelV2(userIdList);
        long end2 = System.currentTimeMillis();
        System.out.println("耗时：" + (end2 - begin2) + "ms");
    }
}
