package 并发编程15.分库分表.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;
import 并发编程15.分库分表.po.UserMessagePO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author idea
 * @Date created in 10:08 下午 2022/8/9
 */
@Service
public interface IUserMessageService extends IService<UserMessagePO> {

    /**
     * 从分表中查询数据
     *
     * @param userId
     * @return
     */
    List<UserMessagePO> selectByUserId(Long userId);

    /**
     * 从分表中查询未读数据
     *
     * @param userId
     * @return
     */
    List<UserMessagePO> selectNotRead(Set<Long> userId);

    /**
     * 往单表中写入数据
     */
    void insertData();

    /**
     * 从源表中将数据写入到各个分表中
     */
    void splitFromSourceTable();
}
