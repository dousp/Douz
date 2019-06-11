package com.dsp.douz.rate.limit.guava.autoconfigure;

import com.dsp.douz.rate.limit.guava.conf.GuavaLimitConf;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Configuration;

/**
 * 这里只是guava limit的自动配置，只导入GuavaLimitConf.class
 * @author Dsp
 */
@Import({ GuavaLimitConf.class })
@Configuration
public class GvaRateLimitAutoConfiguration {

}
