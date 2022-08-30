package 并发编程15.分库分表.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import 并发编程15.分库分表.po.UserMessagePO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author idea
 * @Date created in 10:08 下午 2022/8/9
 */
@Mapper
public interface UserMessageMapper extends BaseMapper<UserMessagePO> {


    List<UserMessagePO> selectByUserId(@Param("userId")Long userId);

    List<UserMessagePO> selectNotRead(@Param("userIds") Set<Long> userIdList);

    void insertToSplitTable(@Param("user") UserMessagePO userMessagePO,@Param("tableIndex")String tableIndex);
}
