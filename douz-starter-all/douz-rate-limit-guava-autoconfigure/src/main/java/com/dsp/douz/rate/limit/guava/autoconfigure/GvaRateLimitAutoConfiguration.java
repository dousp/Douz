package com.dsp.douz.rate.limit.guava.autoconfigure;

import com.dsp.douz.rate.limit.guava.conf.GuavaLimitConf;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dsp
 */
@Import({ GuavaLimitConf.class })
@Configuration
public class GvaRateLimitAutoConfiguration {

}
