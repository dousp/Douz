package com.dsp.douz.rate.limit.guava.core;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author dsp
 *
 * 单机应用调用限流
 */
public class RateLimitCache {

    private static final double RATE = 10d;

    /**
     *  how about "private static volatile ConcurrentMap<String, RateLimiter> CACHE_MAP = new ConcurrentHashMap();" ??
     */
    private static final ConcurrentMap<String, RateLimiterEntity> CACHE = new ConcurrentHashMap<>();

    /**
     * 因为 rateLimiter.setRate(permitsPerSecond)会执行一次synchronized，
     * 为避免不必要的同步，故通过RateLimiterEntity去封装，做一定的冗余设计
     */
    private static class RateLimiterEntity {

        private RateLimiter rateLimiter;
        private double rate;

        public RateLimiterEntity() {}

        public RateLimiterEntity(RateLimiter rateLimiter, double rate) {
            this.rateLimiter = rateLimiter;
            this.rate = rate;
        }

        public RateLimiter getRateLimiter() {
            return rateLimiter;
        }

        public void setRateLimiter(RateLimiter rateLimiter) {
            this.rateLimiter = rateLimiter;
        }

        public double getRate() {
            return rate;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

    }


    private static RateLimiterEntity getRateLimiterEntity(String requestKey) {
        return CACHE.get(requestKey);
    }

    private static RateLimiterEntity cacheRateLimiterEntity(String requestKey, double rate) {
        RateLimiterEntity rateLimiterEntity = CACHE.get(requestKey);
        if (rateLimiterEntity == null) {
            RateLimiter limiter = RateLimiter.create(rate);
            RateLimiterEntity entity = new RateLimiterEntity(limiter,rate);
            rateLimiterEntity = CACHE.putIfAbsent(requestKey, entity);
            if (rateLimiterEntity == null) {
                // 说明这时候已存在该requestKey
                // rateLimiterEntity = entity;
                rateLimiterEntity = CACHE.get(requestKey);
            }
        } else {
            if (rateLimiterEntity.getRate() != rate) {
                rateLimiterEntity.getRateLimiter().setRate(rate);
                rateLimiterEntity.setRate(rate);
            }
        }
        return rateLimiterEntity;
    }

    public static RateLimiter get(String requestKey) {
        RateLimiterEntity rateLimiterEntity = getRateLimiterEntity(requestKey);
        if (rateLimiterEntity == null) {
            return null;
        }
        return rateLimiterEntity.getRateLimiter();
    }

    public static RateLimiter merge(String requestKey, double rate) {
        RateLimiterEntity rateLimiterEntity = cacheRateLimiterEntity(requestKey,rate);
        return rateLimiterEntity.getRateLimiter();
    }

    public static RateLimiter remove(String requestKey){
        return CACHE.remove(requestKey).getRateLimiter();
    }

}
