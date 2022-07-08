package 并发编程11.用户查询.dao;

import 并发编程11.用户查询.bean.UserInfoPO;

/**
 * @Author linhao
 * @Date created in 5:20 下午 2022/7/4
 */
public class UserDao {

    public UserInfoPO queryUserInfo(long userId){
        UserInfoPO userInfoPO = new UserInfoPO();
        userInfoPO.setAge((short) 11);
        userInfoPO.setTel("1677839102");
        userInfoPO.setSex((short) 1);
        userInfoPO.setUserName("idea");
        userInfoPO.setUserId(userId);
        return userInfoPO;
    }

}
