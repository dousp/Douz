package com.dsp.douz.rate.limit.redis.aop;

import com.dsp.douz.rate.limit.redis.annotation.RedisLimit;
import com.dsp.douz.rate.limit.redis.constant.LimitType;
import com.google.common.collect.ImmutableList;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;

@Aspect
@Component
public class RedisLimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(RedisLimitAspect.class);

    private final RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    public RedisLimitAspect(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Around("execution(public * *(..)) && @annotation(com.dsp.douz.rate.limit.redis.annotation.RedisLimit)")
    public Object interceptor(ProceedingJoinPoint pjp) {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RedisLimit redisLimit = method.getAnnotation(RedisLimit.class);
        LimitType limitType = redisLimit.limitType();
        // TODO: 2019-06-10
        String perfix = "";
        int limitPeriod = redisLimit.period();
        int limitQps = redisLimit.qps();
        String name =  StringUtils.upperCase(redisLimit.name());
        String key;
        switch (limitType) {
            case IP:
                key = getIpAddress();
                break;
            case CUSTOMER:
                // TODO: 2019-06-10  自定义key实现，需要分布式锁，防止重复提交等
                key = redisLimit.key();
                break;
            default:
                key = StringUtils.upperCase(method.getName());
        }
        ImmutableList<String> keys = ImmutableList.of(StringUtils.join(perfix,key, name));
        try {
            String luaScript = buildLuaScript();
            RedisScript<Number> redisScript = new DefaultRedisScript<>(luaScript, Number.class);
            Number count = redisTemplate.execute(redisScript, keys, limitQps, limitPeriod);
            logger.info("The current qps is {} for name={} and key = {}", count, name, key);
            if (count != null && count.intValue() <= limitQps) {
                return pjp.proceed();
            } else {
                throw new RuntimeException("Qps reaches the maximum limit");
            }
        } catch (Throwable e) {
            if (e instanceof RuntimeException) {
                throw new RuntimeException(e.getLocalizedMessage());
            }
            throw new RuntimeException("server exception");
        }
    }

    /**
     * 限流 脚本
     *
     * @return lua脚本
     */
    public String buildLuaScript() {
        StringBuilder lua = new StringBuilder();
        lua.append("local c");
        lua.append("\nc = redis.call('get',KEYS[1])");
        // 调用不超过最大值，则直接返回
        lua.append("\nif c and tonumber(c) > tonumber(ARGV[1]) then");
        lua.append("\nreturn c;");
        lua.append("\nend");
        // 执行计算器自加
        lua.append("\nc = redis.call('incr',KEYS[1])");
        lua.append("\nif tonumber(c) == 1 then");
        // 从第一次调用开始限流，设置对应键值的过期
        lua.append("\nredis.call('expire',KEYS[1],ARGV[2])");
        lua.append("\nend");
        lua.append("\nreturn c;");
        return lua.toString();
    }

    private static final String UNKNOWN = "unknown";

    public String getIpAddress() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
