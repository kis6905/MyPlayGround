package com.is.mpg.service.modules;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class RedisClient {
	
	@SuppressWarnings("unused")
	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	
    @Resource(name="redisTemplate")
    private ValueOperations<String, String> valueOps;
    
    /**
     * TODO value Type을 제네릭으로...
     */
    public void set(String key, String value) {
    	valueOps.set(key, (String) value);
    }
    
    public String get(String key) {
    	return valueOps.get(key);
    }
}
