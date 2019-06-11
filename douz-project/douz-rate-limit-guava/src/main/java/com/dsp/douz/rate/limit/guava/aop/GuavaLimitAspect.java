package com.dsp.douz.rate.limit.guava.aop;

import com.dsp.douz.rate.limit.guava.annotation.GuavaLimit;
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
public class GuavaLimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(GuavaLimitAspect.class);

    @Resource
    private RateLimitProxy rateLimitProxy;

    @Pointcut(value = "execution(public * *(..)) && @annotation(com.dsp.douz.rate.limit.guava.annotation.GuavaLimit)")
    public void rateLimitGuava(){}

    @Around(value = "rateLimitGuava()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        GuavaLimit guavaLimit = method.getAnnotation(GuavaLimit.class);
        return rateLimitProxy.invoke(pjp, guavaLimit.name(), guavaLimit.key(), guavaLimit.period(), guavaLimit.qps(), guavaLimit.timeout());
    }

}
