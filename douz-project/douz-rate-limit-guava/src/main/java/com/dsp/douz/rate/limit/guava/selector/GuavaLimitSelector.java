package com.dsp.douz.rate.limit.guava.selector;

import com.dsp.douz.rate.limit.guava.aop.GuavaLimitAspect;
import com.dsp.douz.rate.limit.guava.service.impl.RateLimitGuavaExecutor;
import com.dsp.douz.rate.limit.guava.service.impl.RateLimitGuavaProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.DeferredImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author dsp
 * 通过在使用@Configuration的类上使用 <code> @Import({ GuavaLimitSelector.class }) </>来实现bean注入
 */
public class GuavaLimitSelector implements DeferredImportSelector {

    private static final Logger logger = LoggerFactory.getLogger(GuavaLimitSelector.class);

    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        logger.info("douz-rate-limit-guava begin import...");
        return new String[] { GuavaLimitAspect.class.getName(),
                                        RateLimitGuavaExecutor.class.getName(),
                                        RateLimitGuavaProxy.class.getName()};
    }
}
