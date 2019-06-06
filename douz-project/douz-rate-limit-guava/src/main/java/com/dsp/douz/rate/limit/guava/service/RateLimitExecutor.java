package com.dsp.douz.rate.limit.guava.service;

/**
 * @author dsp
 */
public interface RateLimitExecutor {

    boolean tryAccess(String name, String key, int limitPeriod, int limitCount, long timeout) throws Exception;

    boolean tryAccess(String requestKey, int limitPeriod, int limitCount, long timeout) throws Exception;

    default  String getRequestKey(String name, String key) {
        return name + "_" + key;
    }
}
