package cn.own.boot.fundation.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @description:
 * @author: xinYue
 * @time: 2019/11/21 14:12
 */
@Slf4j
@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 加锁
     * @param key
     * @param value 当前时间+超时时间
     * @return
     */
    public boolean lock(String key, String value){
        //相当于redis的setNf
        if(redisTemplate.opsForValue().setIfAbsent(key,value)){
            return Boolean.TRUE;
        }
        String currentValue = redisTemplate.opsForValue().get(key);
        if(!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) <System.currentTimeMillis()){
            //获取上一个锁的时间
            String oldValue = redisTemplate.opsForValue().getAndSet(key,value);
            if(!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 解锁
     * @param key
     * @param value
     */
    public void unlock(String key, String value){
        try{
            String currentValue = redisTemplate.opsForValue().get(key);
            if(!StringUtils.isEmpty(currentValue) && currentValue.equals(value)){
                redisTemplate.opsForValue().getOperations().delete(key);
            }
        }catch (Exception e){
            log.error("redis分布式锁失效");
        }

    }
}