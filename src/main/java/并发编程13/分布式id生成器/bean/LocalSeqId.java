package com.qiyu.tech.id.builder.bean;

import lombok.Data;
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
