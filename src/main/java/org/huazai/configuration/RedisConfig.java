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

//    @Bean
//    public CachingConfigurerSupport keyGenerator() {
//        return new MyCachingConfigurerSupport();
//    }
//
//    public class MyCachingConfigurerSupport extends CachingConfigurerSupport {
//        @Override
//        public KeyGenerator keyGenerator() {
//            return new KeyGenerator() {
//                @Override
//                public Object generate(Object target, Method method, Object... params) {
//                    StringBuilder sb = new StringBuilder();
//                    sb.append(target.getClass().getName());
//                    sb.append(method.getName());
//                    for (Object obj : params) {
//                        sb.append(obj.toString());
//                    }
//                    return sb.toString();
//                }
//            };
//        }
//    }

//    /**
//     * 存入对象tostring后的信息
//     */
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate() {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(new JedisConnectionFactory());
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jackson2JsonRedisSerializer.setObjectMapper(om);
//
//        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
//        // key采用String的序列化方式
//        template.setKeySerializer(stringRedisSerializer);
//        // value序列化方式采用jackson
//        template.setValueSerializer(jackson2JsonRedisSerializer);
//
//        // hash的key也采用String的序列化方式,需要配置一下StringSerializer,不然key会乱码 /XX/XX
//        template.setHashKeySerializer(stringRedisSerializer);
//        // hash的value序列化方式采用jackson
//        template.setHashValueSerializer(jackson2JsonRedisSerializer);
//
//        template.afterPropertiesSet();
//        return template;
//    }

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
