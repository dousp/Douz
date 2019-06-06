package com.dsp.douz.rate.limit.guava.service;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @author dsp
 */
public interface RateLimitProxy {

    Object invoke(ProceedingJoinPoint invocation, String name, String key, int limitPeriod, int limitCount, long timeout) throws Throwable;
}
