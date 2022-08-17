package 并发编程13.分布式id生成器.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import 并发编程13.分布式id生成器.bean.LocalSeqId;
import 并发编程13.分布式id生成器.bean.po.IdBuilderPO;
import 并发编程13.分布式id生成器.dao.mapper.IdBuilderMapper;
import 并发编程13.分布式id生成器.service.IdBuilderService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @Author idea
 * @Date created in 11:18 下午 2020/9/17
 */
@Service
@Slf4j
public class IdBuilderServiceImpl implements IdBuilderService, InitializingBean {

    private static ConcurrentHashMap<Integer, BitSet> bitSetMap = new ConcurrentHashMap<>();
    private static Map<Integer, LocalSeqId> localSeqMap;

    private final static Logger LOGGER = LoggerFactory.getLogger(IdBuilderServiceImpl.class);

    @Resource
    private IdBuilderMapper idBuilderMapper;

    /**
     * 考虑分布式环境下 多个请求同时更新同一行数据的情况
     *
     * @param idBuilderPO
     * @return
     */
    private IdBuilderPO updateIdBuilderConfig(IdBuilderPO idBuilderPO) {
        int updateResult = -1;
        //假设重试过程中出现网络异常，那么使用cas的时候必须要考虑退出情况 极限情况下更新100次
        for (int i = 0; i < 100; i++) {
            try {
                IdBuilderPO newIdBuilderPO = idBuilderMapper.selectOne(idBuilderPO.getId());
                long nextThreshold = newIdBuilderPO.getNextThreshold();
                long step = newIdBuilderPO.getStep();
                updateResult = idBuilderMapper.updateCurrentThreshold(nextThreshold + step, nextThreshold, newIdBuilderPO.getId(), newIdBuilderPO.getVersion());
                if (updateResult > 0) {
                    return newIdBuilderPO;
                }
            } catch (Exception e) {
                LOGGER.error("[updateIdBuilderConfig] error is ", e);
            }
        }
        return null;
    }


    @Override
    public void afterPropertiesSet() {
        //在启动环境，预先初始化好id数据
        List<IdBuilderPO> idBuilderPOS = idBuilderMapper.selectAll();
        List<IdBuilderPO> refreshList = new ArrayList<>();
        for (IdBuilderPO idBuilderPO : idBuilderPOS) {
            //每次重启到时候，都需要将之前的上一个区间的id全部抛弃，使用新的步长区间
            refreshList.add(updateIdBuilderConfig(idBuilderPO));
        }
        localSeqMap = new ConcurrentHashMap<>(refreshList.size());
        for (IdBuilderPO idBuilderPO : refreshList) {
            //有序的id段由一个LocalSeqId管理
            LocalSeqId localSeqId = new LocalSeqId();
            localSeqId.setNextUpdateId(new AtomicLong(idBuilderPO.getNextThreshold()+idBuilderPO.getStep()));
            localSeqId.setCurrentId(new AtomicLong(idBuilderPO.getNextThreshold()));
            localSeqId.setStep(idBuilderPO.getStep());
            localSeqId.setIdPrefix(idBuilderPO.getIdPrefix());
            localSeqMap.put(idBuilderPO.getId(), localSeqId);
        }
        //自动预先加载新的id段到本地缓存中
        new Thread(new refreshLocalIdCacheJob()).start();
    }


    /**
     * 内部判断是否需要刷新本地id步长数据
     */
    class refreshLocalIdCacheJob implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                    for (Integer code : localSeqMap.keySet()) {
                        //这里做一个校验,确认此时本地id段确实需要更新
                        LocalSeqId localSeqId = localSeqMap.get(code);
                        if (localSeqId.getNextUpdateId().get() - localSeqId.getCurrentId().get() > localSeqId.getStep() * 0.25) {
                            continue;
                        }
                        int updateResult = -1;
                        //如果更新失败，进行重试
                        for (int i = 0; i < 100; i++) {
                            IdBuilderPO newIdBuilderPO = idBuilderMapper.selectOne(code);
                            long nextThreshold = newIdBuilderPO.getNextThreshold();
                            long currentStart = newIdBuilderPO.getCurrentStart();
                            long step = newIdBuilderPO.getStep();
                            updateResult = idBuilderMapper.updateCurrentThreshold(nextThreshold + step, currentStart + step, code, newIdBuilderPO.getVersion());
                            if (updateResult > 0) {
                                localSeqId = new LocalSeqId();
                                localSeqId.setCurrentId(new AtomicLong(nextThreshold + 1));
                                localSeqId.setIdPrefix(newIdBuilderPO.getIdPrefix());
                                localSeqId.setStep(newIdBuilderPO.getStep());
                                localSeqId.setNextUpdateId(new AtomicLong(nextThreshold + step));
                                localSeqMap.put(code, localSeqId);
                                LOGGER.info("更新id本地步长成功");
                                break;
                            }
                        }
                    }
                } catch (InterruptedException e) {
                    LOGGER.error("e is ", e);
                }
            }
        }
    }

    @Override
    public Long increaseSeqId(int code) {
        //直接从本地缓存中提取id数据
        LocalSeqId localSeqId = localSeqMap.get(code);
        if (localSeqId == null) {
            LOGGER.error("[increaseSeqId] code:{} is error", code);
            return null;
        }
        //原子性自增操作
        long result = localSeqId.getCurrentId().getAndAdd(1);
        return result;
    }

    @Override
    public String increaseSeqStrId(int code) {
        LocalSeqId localSeqId = localSeqMap.get(code);
        if (localSeqId == null) {
            LOGGER.error("[increaseSeqStrId] code:{} is error", code);
            return null;
        }
        //原子性自增操作
        long result = localSeqId.getCurrentId().getAndAdd(1);
        return localSeqId.getIdPrefix() + result;
    }
}
