package 并发编程15.分库分表.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @Author idea
 * @Date created in 10:04 下午 2022/8/9
 */
@Data
@TableName("t_user_message")
public class UserMessagePO {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long objectId;

    //关联id
    private Long relationId;

    private Integer isRead;

    private Integer sid;

    private Integer status;

    private String content;

    private Integer type;

    private String extJson;

    private Date createTime;

    private Date updateTime;

}
