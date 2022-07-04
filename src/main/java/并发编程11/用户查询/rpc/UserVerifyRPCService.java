package 并发编程11.用户查询.rpc;

/**
 * @Author linhao
 * @Date created in 6:46 下午 2022/7/4
 */
public class UserVerifyRPCService {

    public boolean queryUserVerifyStatus(long userId){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    }
}
