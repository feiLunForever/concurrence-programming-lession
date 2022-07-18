package 并发编程11.用户查询.service;

import org.springframework.beans.BeanUtils;
import 并发编程11.IUserQueryService;
import 并发编程11.用户查询.bean.UserInfoDTO;
import 并发编程11.用户查询.bean.UserInfoPO;
import 并发编程11.用户查询.dao.UserDao;
import 并发编程11.用户查询.rpc.MemberLevelRPCService;
import 并发编程11.用户查询.rpc.UserHeadPortraitRPCService;
import 并发编程11.用户查询.rpc.UserVerifyRPCService;


/**
 * @Author idea
 * @Date created in 6:29 下午 2022/7/4
 */
public class UserQueryService implements IUserQueryService {

    private UserDao userDao = new UserDao();

    private UserVerifyRPCService userVerifyRPCService = new UserVerifyRPCService();
    private MemberLevelRPCService memberLevelRPCService = new MemberLevelRPCService();
    private UserHeadPortraitRPCService userHeadPortraitRPCService = new UserHeadPortraitRPCService();


    @Override
    public UserInfoDTO queryUserInfoWrapper(long userId) {
        UserInfoPO userInfoPO = userDao.queryUserInfo(userId);
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfoPO, userInfoDTO);
        userInfoDTO.setMemberLevel(memberLevelRPCService.queryUserLevel(userId));
        userInfoDTO.setHeadPortrait(userHeadPortraitRPCService.queryUserHeadPortrait(userId));
        userInfoDTO.setVerifyStatus(userVerifyRPCService.queryUserVerifyStatus(userId));
        return userInfoDTO;
    }

    public static void main(String[] args) {
        UserQueryService userQueryService = new UserQueryService();
        long begin = System.currentTimeMillis();
        UserInfoDTO userInfoDTO = userQueryService.queryUserInfoWrapper(1001);
        System.out.println(userInfoDTO);
        long end = System.currentTimeMillis();
        System.out.println("查询耗时：" + (end - begin));
    }

}
