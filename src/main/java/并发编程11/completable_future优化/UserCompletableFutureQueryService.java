package 并发编程11.completable_future优化;

import org.springframework.beans.BeanUtils;
import 并发编程11.IUserQueryService;
import 并发编程11.用户查询.bean.UserInfoDTO;
import 并发编程11.用户查询.bean.UserInfoPO;
import 并发编程11.用户查询.dao.UserDao;
import 并发编程11.用户查询.rpc.MemberLevelRPCService;
import 并发编程11.用户查询.rpc.UserHeadPortraitRPCService;
import 并发编程11.用户查询.rpc.UserVerifyRPCService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @Author idea
 * @Date created in 7:15 下午 2022/7/4
 */
public class UserCompletableFutureQueryService implements IUserQueryService {

    private UserDao userDao = new UserDao();

    private UserVerifyRPCService userVerifyRPCService = new UserVerifyRPCService();
    private MemberLevelRPCService memberLevelRPCService = new MemberLevelRPCService();
    private UserHeadPortraitRPCService userHeadPortraitRPCService = new UserHeadPortraitRPCService();

    @Override
    public UserInfoDTO queryUserInfoWrapper(long userId) {
        UserInfoPO userInfoPO = userDao.queryUserInfo(userId);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoPO, userInfoDTO);
        CompletableFuture userVerifyFuture = CompletableFuture.supplyAsync(new Supplier<Boolean>() {
            @Override
            public Boolean get() {
                return userVerifyRPCService.queryUserVerifyStatus(userId);
            }
        });

        userVerifyFuture.thenAccept(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean state) {
                userInfoDTO.setVerifyStatus(state);
            }
        });

        CompletableFuture memberLevelFuture = CompletableFuture.supplyAsync(new Supplier<Integer>() {
            @Override
            public Integer get() {
                return memberLevelRPCService.queryUserLevel(userId);
            }
        });
        memberLevelFuture.thenAccept(new Consumer<Integer>() {
            @Override
            public void accept(Integer level) {
                userInfoDTO.setMemberLevel(level);
            }
        });

        CompletableFuture userHeadPortraitFuture = CompletableFuture.supplyAsync(new Supplier<List<String>>() {
            @Override
            public List<String> get() {
                return userHeadPortraitRPCService.queryUserHeadPortrait(userId);
            }
        });
        userHeadPortraitFuture.thenAccept(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> resultList) {
                userInfoDTO.setHeadPortrait(resultList);
            }
        });
        return userInfoDTO;
    }

    public static void main(String[] args) {
        UserCompletableFutureQueryService userCompletableFutureQueryService = new UserCompletableFutureQueryService();
        long begin = System.currentTimeMillis();
        UserInfoDTO userInfoDTO = userCompletableFutureQueryService.queryUserInfoWrapper(1001);
        long end = System.currentTimeMillis();
        System.out.println("请求耗时：" + (end - begin) + "," + userInfoDTO);
    }
}
