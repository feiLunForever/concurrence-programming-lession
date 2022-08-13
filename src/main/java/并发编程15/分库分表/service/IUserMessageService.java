package 并发编程15.分库分表.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import 并发编程15.分库分表.po.UserMessagePO;

/**
 * @Author linhao
 * @Date created in 10:08 下午 2022/8/9
 */
@Service
public interface IUserMessageService extends IService<UserMessagePO> {

    void doQueryFromSplitTable(Long userId);
}
