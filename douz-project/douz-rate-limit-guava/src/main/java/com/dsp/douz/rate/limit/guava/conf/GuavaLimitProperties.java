package com.dsp.douz.rate.limit.guava.conf;

import com.dsp.douz.rate.limit.guava.constant.GuavaLimitConstant;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author dd
 * @date 2019-05-27
 */
@Configuration
@ConfigurationProperties(GuavaLimitConstant.DOUZ_PREFIX)
public class GuavaLimitProperties {

    /**
     * 是否启用
     */
    private Boolean enabled;
    /**
     * 配置key前缀
     */
    private String keyPrefix = "douz_glimit";
    /**
     * 限流异常后是否继续执行方法
     */
    private Boolean exceptionIgnore;
    /**
     * 限流类型
     */
    private String type;


    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getExceptionIgnore() {
        return exceptionIgnore;
    }

    public void setExceptionIgnore(Boolean exceptionIgnore) {
        this.exceptionIgnore = exceptionIgnore;
    }


}
