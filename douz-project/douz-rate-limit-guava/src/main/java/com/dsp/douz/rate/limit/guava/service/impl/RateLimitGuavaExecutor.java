package com.dsp.douz.rate.limit.guava.service.impl;

import com.dsp.douz.rate.limit.guava.core.RateLimitCache;
import com.dsp.douz.rate.limit.guava.exception.RateLimitGuavaRuntimeException;
import com.dsp.douz.rate.limit.guava.service.RateLimitExecutor;
import com.google.common.util.concurrent.RateLimiter;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

/**
 * @author dsp
 */
public class RateLimitGuavaExecutor implements RateLimitExecutor {

    @Override
    public boolean tryAccess(String name, String key, int limitPeriod, int limitCount,  long timeout){
        return tryAccess(getRequestKey(name,key), limitPeriod, limitCount,  timeout);
    }

    @Override
    public boolean tryAccess(String requestKey, int limitPeriod, int limitCount,  long timeout) {
        // requestKey设置验证
        if (StringUtils.isEmpty(requestKey)) {
            throw new RateLimitGuavaRuntimeException("Request key is empty");
        }
        // 限流周期设置验证
        // if (limitPeriod != 1) {
        //     limitPeriod = 1;
        // }
        // 超时设置验证
        RateLimiter rateLimiter = RateLimitCache.merge(requestKey, limitCount);
        if(timeout == 0){
            return false;
        }else if(timeout < 0){
            return rateLimiter.tryAcquire();
        }
        return rateLimiter.tryAcquire(timeout, TimeUnit.MILLISECONDS);
    }

}
