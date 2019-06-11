package com.dsp.douz.rate.limit.redis.conf;

import com.dsp.douz.rate.limit.redis.constant.RedisLimitConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dsp
 */
@Configuration
@ConfigurationProperties(RedisLimitConstant.DOUZ_REDIS_LIMIT)
public class RedisLimitProperties {

    /**
     * 分布式限流key前缀
     */
    private String prefix = "douz_rlimit";

    /**
     * 是否启用
     */
    private Boolean enabled;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
