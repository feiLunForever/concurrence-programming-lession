package 并发编程13.分布式id生成器.bean;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

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
    private AtomicLong currentId;

    /**
     * 步长
     */
    private int step;

    /**
     * 达到这个阈值就需要进行更新
     */
    private AtomicLong nextUpdateId;

    /**
     * id的前缀
     */
    private String idPrefix;

}
