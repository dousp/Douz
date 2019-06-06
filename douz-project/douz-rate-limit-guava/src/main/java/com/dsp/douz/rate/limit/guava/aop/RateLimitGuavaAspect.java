package com.dsp.douz.rate.limit.guava.aop;

import com.dsp.douz.rate.limit.guava.annotation.RateLimitGuava;
import com.dsp.douz.rate.limit.guava.exception.RateLimitGuavaRuntimeException;
import com.dsp.douz.rate.limit.guava.service.RateLimitProxy;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * @author dsp
 */
@Aspect
public class RateLimitGuavaAspect {

    private static final Logger logger = LoggerFactory.getLogger(RateLimitGuavaAspect.class);

    @Resource
    private RateLimitProxy rateLimitProxy;

    @Pointcut(value = "execution(public * *(..)) && @annotation(com.dsp.douz.rate.limit.guava.annotation.RateLimitGuava)")
    public void rateLimitGuava(){}

    @Around(value = "rateLimitGuava()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RateLimitGuava rateLimitGuava = method.getAnnotation(RateLimitGuava.class);
        return rateLimitProxy.invoke(pjp,rateLimitGuava.name(), rateLimitGuava.key(), rateLimitGuava.period(), rateLimitGuava.qps(),rateLimitGuava.timeout());
    }

}
