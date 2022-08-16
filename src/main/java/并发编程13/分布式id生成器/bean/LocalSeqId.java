package 并发编程13.分布式id生成器.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author linhao
 * @Date created in 10:56 上午 2020/12/19
 */
@Getter
@Setter
public class LocalSeqId {

    /**
     * 本地缓存里当前存储的id数值
     */
    private long currentId;

    /**
     * 达到这个阈值就需要进行更新
     */
    private long nextUpdateId;

}
