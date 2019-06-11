package com.dsp.douz.rate.limit.redis.conf;

import com.dsp.douz.rate.limit.redis.constant.RedisLimitConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(RedisLimitConstant.DOUZ_REDIS_LIMIT)
public class RedisLimitProperties {

    private String prefix = "douz_rlimit";

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
