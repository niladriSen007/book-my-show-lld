package com.niladri.book_my_show.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheServiceImpl implements ICacheService {

    private final StringRedisTemplate redisTemplate;

    @Override
    public void setKey(String key, Object value) {
        redisTemplate.opsForValue().set(key, value.toString(), 2, TimeUnit.MINUTES);
    }

    @Override
    public Object getKey(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteKey(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void getAllKeyAndValues() {
        redisTemplate.keys("*").forEach(key -> {
            String value = redisTemplate.opsForValue().get(key);
            log.info("Key: " + key + ", Value: " + value);
        });
    }

    @Override
    public void deleteAll() {
        redisTemplate.delete(redisTemplate.keys("*"));
    }
}
