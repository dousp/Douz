package com.dsp.douz.rate.limit.redis.constant;

public enum LimitType {

    /**
     * 自定义key
     */
    CUSTOMER,
    /**
     * 根据请求者IP
     */
    IP;

}
