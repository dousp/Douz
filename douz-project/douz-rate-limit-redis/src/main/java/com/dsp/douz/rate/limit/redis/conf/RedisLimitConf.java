package com.dsp.douz.rate.limit.redis.conf;

import com.dsp.douz.rate.limit.redis.aop.RedisLimitAspect;
import com.dsp.douz.rate.limit.redis.constant.RedisLimitConstant;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

@Configuration
@EnableConfigurationProperties(RedisLimitProperties.class)
@ConditionalOnProperty(prefix = RedisLimitConstant.DOUZ_PREFIX, value = RedisLimitConstant.ENABLED, havingValue = "true")
@ConditionalOnMissingBean({RedisTemplate.class,RedisLimitAspect.class})
public class RedisLimitConf {

    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory){
        RedisTemplate<String, Serializable> template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setConnectionFactory(lettuceConnectionFactory);
        return template;
    }
}
