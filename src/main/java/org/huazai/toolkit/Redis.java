package org.huazai.toolkit;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Component
public class Redis {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    // common operation

    public Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public void expire(String key, long timeout, TimeUnit unit) {
        redisTemplate.expire(key, timeout, unit);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 通过类型获取对应的对象类型
     * redis存储的某些字段和实际的字段不一致需要额外转换
     * @param object 从redis中直接拿出来的对象
     * @param clazz  想要转换的目标类型
     * @return 转化后的对象
     */
    @SuppressWarnings("unchecked")
    private <T> T getByType(Object object, Class<T> clazz) {
        if ((clazz == Long.class || clazz == long.class) && object instanceof Integer) {
            Integer integerObject = (Integer) object;
            return (T) Long.valueOf(integerObject.longValue());
        } else if ((clazz == Short.class || clazz == short.class) && object instanceof Integer) {
            Integer integerObject = (Integer) object;
            return (T) Short.valueOf(integerObject.shortValue());
        } else if ((clazz == Byte.class || clazz == byte.class) && object instanceof Integer) {
            Integer integerObject = (Integer) object;
            return (T) Byte.valueOf(integerObject.byteValue());
        } else if ((clazz == Float.class || clazz == float.class) && object instanceof Double) {
            Double doubleObject = (Double) object;
            return (T) Float.valueOf(doubleObject.floatValue());
        } else if ((clazz == Character.class || clazz == char.class) && object instanceof String) {
            String StringObject = (String) object;
            return (T) Character.valueOf(StringObject.charAt(0));
        } else {
            return (T) object;
        }
    }

    // string operation

    public void set(String key, Object value, long timeout, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * get, 结果直接强转就可以了
     * String value = (String) redis.get("key");
     * List<String> values = (List<String>) redis.get("key");
     * UserDTO value = (UserDTO) redis.get("user");
     * 注意: Long,Short,Byte. Float. Character 等类型需要做特殊处理
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public <T> T get(String key, Class<T> clazz) {
        Object object = redisTemplate.opsForValue().get(key);
        return getByType(object, clazz);
    }

    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 自增delta
     */
    public Long increment(String key, Long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

    public Long decrement(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }

    /**
     * 自减delta
     */
    public Long decrement(String key, Long delta) {
        return redisTemplate.opsForValue().decrement(key, delta);
    }

    /**
     * setNx
     * absent:不存在
     * present:存在
     * key不存在,执行set,返回true,否则返回false
     */
    public Boolean setIfAbsent(String key, Object value, long timeout, TimeUnit unit) {
        return redisTemplate.opsForValue().setIfAbsent(key, value, timeout, unit);
    }

    /**
     * 获取指定类型的结果
     */
    public <T> T getObject(final String key, final Class<T> clazz) {
        Object obj = redisTemplate.opsForValue().get(key);
        return JSON.parseObject(JSON.toJSONString(obj), clazz);
    }

    /**
     * increment(建议首次递增使用这个，递增方法如果不设置过期时间，就是永久储存)
     * 递增，每次加1
     * 并设置过期时间
     */
    public Long increment(String key, long timeout, TimeUnit timeUnit) {
        Long val = redisTemplate.opsForValue().increment(key);
        if (val != null && val <= 1L) {
            redisTemplate.expire(key, timeout, timeUnit);
        }
        return val;
    }

    /**
     * increment(建议首次递增使用这个，递增方法如果不设置过期时间，就是永久储存)
     * 递增，每次加去给定的值
     * 并设置过期时间
     */
    public Long increment(String key, Long value, long timeout, TimeUnit timeUnit) {
        Long val = redisTemplate.opsForValue().increment(key, value);
        if (val != null && val <= 1L) {
            redisTemplate.expire(key, timeout, timeUnit);
        }
        return val;
    }

    // hash operation

    public boolean hashHasKey(String key, String subKey) {
        return redisTemplate.opsForHash().hasKey(key, subKey);
    }

    public void hashSet(String key, String subKey, Object value) {
        redisTemplate.opsForHash().put(key, subKey, value);
    }

    public <T> T hashGet(String key, String subKey, Class<T> clazz) {
        Object object = redisTemplate.opsForHash().get(key, subKey);
        return getByType(object, clazz);
    }

    public Object hashDelete(String key, String subKey) {
        return redisTemplate.opsForHash().delete(key, subKey);
    }

    public Map<String, Object> hashEntries(String key) {
        return redisTemplate.opsForHash().entries(key).entrySet().stream().collect(Collectors.toMap(entry -> (String) entry.getKey(), Map.Entry::getValue));
    }

    /**
     * 获取hash的所有key
     * @author YanAnHuaZai
     * create 2020年08月06日21:55:44
     * @param key redis的key
     * @param clazz hash的key的类型
     * @return redis key下所有的hash key
     */
    public <T> Set<T> hashKeys(String key, Class<T> clazz) {
        return redisTemplate.opsForHash().keys(key).stream().map(item -> getByType(item, clazz)).collect(Collectors.toSet());
    }

    public <V> Map<String, V> hashEntries(String key, Class<V> vClazz) {
        return hashEntries(key).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,  entry -> getByType(entry.getValue(), vClazz)));
    }

    public Long hashIncrement(String key, String subKey) {
        return redisTemplate.opsForHash().increment(key, subKey, 1);
    }

    public Long hashDecrement(String key, String subKey) {
        return redisTemplate.opsForHash().increment(key, subKey, -1);
    }

    /**
     * hashIncrement
     */
    public Long hashIncrement(String key, String subKey, long delta, long timeout, TimeUnit unit) {
        Long value = redisTemplate.opsForHash().increment(key, subKey, delta);
        if (value != null && value <= 1L) {
            redisTemplate.expire(key, timeout, unit);
        }
        return value;
    }

}
