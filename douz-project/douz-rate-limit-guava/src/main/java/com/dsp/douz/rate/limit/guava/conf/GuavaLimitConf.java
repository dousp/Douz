package com.dsp.douz.rate.limit.guava.conf;

import com.dsp.douz.rate.limit.guava.constant.GuavaLimitConstant;
import com.dsp.douz.rate.limit.guava.selector.GuavaLimitSelector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Dsp
 */
@Configuration
@EnableConfigurationProperties(GuavaLimitProperties.class)
@ConditionalOnProperty(prefix = GuavaLimitConstant.DOUZ_LIMIT_GUAVA, value = GuavaLimitConstant.ENABLED, havingValue = "true")
@Import({ GuavaLimitSelector.class })
public class GuavaLimitConf {

    // @Resource
    // private LimitProperties limitProperties;
    //
    // @Bean
    // @ConditionalOnMissingBean
    // public RateLimitProxy localLimitProxy() {
    //     return new RateLimitGuavaProxy();
    // }
    //
    // @Bean
    // @ConditionalOnMissingBean
    // public RateLimitExecutor localLimitExecutor() {
    //     return new RateLimitGuavaExecutor();
    // }

}
