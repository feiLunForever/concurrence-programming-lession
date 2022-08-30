package 并发编程13.分布式id生成器.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import 并发编程13.分布式id生成器.bean.po.IdBuilderPO;

import java.util.List;

/**
 * @Author idea
 * @Date created in 11:50 下午 2022/8/16
 */
@Mapper
public interface IdBuilderMapper extends BaseMapper<IdBuilderPO> {

    @Select("select * from t_id_builder_config")
    List<IdBuilderPO> selectAll();

    @Select("select * from t_id_builder_config where id=#{id} limit 1 ")
    IdBuilderPO selectOne(@Param("id") int id);

    @Update("UPDATE t_id_builder_config set next_threshold=#{nextThreshold},current_start=#{currentStart},version=version+1 where id=#{id} and version=#{version}")
    Integer updateCurrentThreshold(@Param("nextThreshold") long nextThreshold, @Param("currentStart") long currentStart, @Param("id") int id, @Param("version") int version);

}
