package 并发编程12.provider.facade;

import dubbo.constants.RequestContentConstants;
import dubbo.context.CommonRequest;
import dubbo.context.CommonRequestContext;
import dubbo.interfaces.UserRpcService;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @Author linhao
 * @Date created in 7:37 下午 2022/7/5
 */
@DubboService
public class UserRpcServiceImpl implements UserRpcService {

    public Executor executor = Executors.newSingleThreadExecutor();

    @Override
    public boolean isUserExist(long id) {
        System.out.println("[UserRpcService] isUserExist is " + true);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Object o = CommonRequestContext.get(RequestContentConstants.COMMON_REQUEST);
                if(o==null){
                    System.out.println("o is null");
                } else {
                    CommonRequest commonRequest = (CommonRequest) o;
                    System.out.println("this is async thread! commonRequest is " + commonRequest);
                }
            }
        });
        return true;
    }

}
