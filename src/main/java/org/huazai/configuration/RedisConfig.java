package org.huazai.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by HASEE on 2018/3/14.
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisSerializer<String> keySerializer() {
        return new StringRedisSerializer();
    }

    @Bean
    public RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory,
                                                       RedisSerializer<String> keySerializer,
                                                       RedisSerializer<Object> valueSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(keySerializer);
        redisTemplate.setValueSerializer(valueSerializer);
        redisTemplate.setHashKeySerializer(keySerializer);
        redisTemplate.setHashValueSerializer(valueSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
