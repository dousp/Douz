package com.dsp.douz.rate.limit.guava.annotation;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RateLimitGuava {

    /**
     * 资源的key
     * @return String
     */
    String key() default "";

    /**
     * 资源的名字
     * @return String
     */
    String name() default "";

    /**
     * 资源限制的时间周期
     * 该属性不管改为何值都是1秒周期，具体原因参见guava
     * 单位：秒
     * @return int
     */
    int period() default 1;

    /**
     * 资源访问限制次数
     * @return int
     */
    int qps() default 10;

    /**
     * 获取令牌的超时毫秒.
     * @return
     */
    long timeout() default 1000;


}
