package 并发编程13.分布式id生成器.service;

/**
 * @Author idea
 * @Date created in 12:16 上午 2022/8/17
 */
public interface IdBuilderService {

    /**
     * 根据本地步长度来生成唯一id(区间性递增)
     *
     * @return
     */
    Long increaseSeqId(int code);


    /**
     * 根据本地步长度来生成唯一id(区间性递增)
     *
     * @return
     */
    String increaseSeqStrId(int code);

}
