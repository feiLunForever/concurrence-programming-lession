package 并发编程15.分库分表.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import 并发编程15.分库分表.po.UserMessagePO;

import java.util.List;
import java.util.Map;

/**
 * @Author linhao
 * @Date created in 10:08 下午 2022/8/9
 */
@Mapper
public interface UserMessageMapper extends BaseMapper<UserMessagePO> {


    List<UserMessagePO> selectByUserId(Map<String,Object> param);

    List<UserMessagePO> selectNotReply(Map<String,Object> param);
}
