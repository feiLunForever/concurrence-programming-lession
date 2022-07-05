package 并发编程11.future优化.service;

import org.springframework.beans.BeanUtils;
import 并发编程11.IUserQueryService;
import 并发编程11.用户查询.bean.UserInfoDTO;
import 并发编程11.用户查询.bean.UserInfoPO;
import 并发编程11.用户查询.dao.UserDao;
import 并发编程11.用户查询.rpc.MemberLevelRPCService;
import 并发编程11.用户查询.rpc.UserHeadPortraitRPCService;
import 并发编程11.用户查询.rpc.UserVerifyRPCService;

import java.util.List;
import java.util.concurrent.*;

/**
 * @Author idea
 * @Date created in 6:55 下午 2022/7/4
 */
public class UserFutureQueryService implements IUserQueryService {

    private UserDao userDao = new UserDao();

    private UserVerifyRPCService userVerifyRPCService = new UserVerifyRPCService();
    private MemberLevelRPCService memberLevelRPCService = new MemberLevelRPCService();
    private UserHeadPortraitRPCService userHeadPortraitRPCService = new UserHeadPortraitRPCService();

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 8, 3000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1000));

    @Override
    public UserInfoDTO queryUserInfoWrapper(long userId) {
        UserInfoPO userInfoPO = userDao.queryUserInfo(userId);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoPO, userInfoDTO);

        Future<Integer> memberLevelFuture = executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() {
                return memberLevelRPCService.queryUserLevel(userId);
            }
        });

        Future<List<String>> userHeadPortraitFuture = executor.submit(new Callable<List<String>>() {
            @Override
            public List<String> call() {
                return userHeadPortraitRPCService.queryUserHeadPortrait(userId);
            }
        });

        Future<Boolean> userVerifyStatusFuture = executor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return userVerifyRPCService.queryUserVerifyStatus(userId);
            }
        });

        try {
            userInfoDTO.setMemberLevel(memberLevelFuture.get(1, TimeUnit.SECONDS));
            userInfoDTO.setHeadPortrait(userHeadPortraitFuture.get(1, TimeUnit.SECONDS));
            userInfoDTO.setVerifyStatus(userVerifyStatusFuture.get(1, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return userInfoDTO;
    }

    public static void main(String[] args) {
        UserFutureQueryService userFutureQueryService = new UserFutureQueryService();
        long begin = System.currentTimeMillis();
        UserInfoDTO userInfoDTO = userFutureQueryService.queryUserInfoWrapper(1001);
        long end = System.currentTimeMillis();
        System.out.println("请求耗时：" + (end - begin) + "," + userInfoDTO);
    }
}
