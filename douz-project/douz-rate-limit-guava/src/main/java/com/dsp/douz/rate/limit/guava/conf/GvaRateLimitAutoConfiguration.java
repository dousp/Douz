package com.dsp.douz.rate.limit.guava.conf;

import com.dsp.douz.rate.limit.guava.constant.LimitConstant;
import com.dsp.douz.rate.limit.guava.selector.GuavaLimitSelector;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author Dsp
 */
@Configuration
@EnableConfigurationProperties(LimitProperties.class)
@ConditionalOnProperty(prefix = LimitConstant.DOUZ_PREFIX, value = LimitConstant.ENABLED, havingValue = "true", matchIfMissing = false)
@Import({ GuavaLimitSelector.class })
public class GvaRateLimitAutoConfiguration {

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
