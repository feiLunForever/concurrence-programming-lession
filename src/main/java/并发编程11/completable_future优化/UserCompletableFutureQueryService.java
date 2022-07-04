package 并发编程11.completable_future优化;

import 并发编程11.IUserQueryService;
import 并发编程11.用户查询.bean.UserInfoDTO;
import 并发编程11.用户查询.dao.UserDao;

/**
 * @Author linhao
 * @Date created in 7:15 下午 2022/7/4
 */
public class UserCompletableFutureQueryService implements IUserQueryService {

    private UserDao userDao = new UserDao();

    @Override
    public UserInfoDTO queryUserInfoWrapper(long userId) {

        return null;
    }
}
