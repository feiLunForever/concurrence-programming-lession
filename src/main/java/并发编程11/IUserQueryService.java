package 并发编程11;

import 并发编程11.用户查询.bean.UserInfoDTO;

/**
 * @Author idea
 * @Date created in 6:54 下午 2022/7/4
 */
public interface IUserQueryService {

    /**
     * 按照id查询
     *
     * @param userId
     * @return
     */
    UserInfoDTO queryUserInfoWrapper(long userId);
}
