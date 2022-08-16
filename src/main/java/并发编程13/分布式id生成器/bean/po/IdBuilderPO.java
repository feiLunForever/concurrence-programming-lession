package 并发编程13.分布式id生成器.bean.po;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @Author idea
 * @Date created in 10:15 上午 2020/9/26
 */
@TableName(value = "t_id_builder_config")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class IdBuilderPO {

    @TableId(type = IdType.AUTO)
    private int id;

    /**
     * id备注描述
     */
    private String desc;

    /**
     * 初始化值
     */
    private long initNum;

    /**
     * 步长
     */
    private int step;

    /**
     * 当前id所在阶段的阈值
     */
    private long nextThreshold;

    /**
     * 业务代码前缀
     */
    private String bizCode;

    /**
     * 乐观锁版本号
     */
    private int version;

    /**
     * 是否需要保证id连续有序性 ，0 是  1 不是
     */
    private short isSeq;

    private Date createTime;

    private Date updateTime;

}
