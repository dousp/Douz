package com.dsp.douz.rate.limit.guava.service.impl;

import com.dsp.douz.rate.limit.guava.conf.GuavaLimitProperties;
import com.dsp.douz.rate.limit.guava.exception.GuavaLimitRuntimeException;
import com.dsp.douz.rate.limit.guava.service.RateLimitExecutor;
import com.dsp.douz.rate.limit.guava.service.RateLimitProxy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author dsp
 */
public class RateLimitGuavaProxy implements RateLimitProxy {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitGuavaProxy.class);

    @Resource
    private RateLimitExecutor rateLimitExecutor;

    @Resource
    private GuavaLimitProperties guavaLimitProperties;

    @Override
    public Object invoke(ProceedingJoinPoint invocation, String name, String key, int limitPeriod, int limitCount, long timeout) throws Throwable {
        boolean status;

        try {
            status = rateLimitExecutor.tryAccess(key, limitPeriod, limitCount, timeout);
        } catch (Exception e) {
            if (this.guavaLimitProperties.getExceptionIgnore()) {
                logger.error("Exception occurs while limit, but the conf --> exceptionIgnore is true", e);
                return invocation.proceed();
            } else {
                logger.error("Exception occurs while limit,", e);
                throw e;
            }
        }

        if (status) {
            return invocation.proceed();
        } else {
            throw new GuavaLimitRuntimeException("Reach max limited access count=" + limitCount + " within period=" + limitPeriod + " seconds");
        }
    }
}
