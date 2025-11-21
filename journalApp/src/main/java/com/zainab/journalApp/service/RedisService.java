package com.zainab.journalApp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zainab.journalApp.api.response.QuoteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;

    public <T> T get(String key, Class<T> entityClass){
        try{
            Object o=redisTemplate.opsForValue().get(key );
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(),entityClass);

        } catch (Exception e) {
           log.error("Exception",e);
           return null;
        }
    }


    public void set(String key, Object o, Long ttl){
        try{
          ObjectMapper mapper = new ObjectMapper();
          String jsonVlaue= mapper.writeValueAsString(o);
          redisTemplate.opsForValue().set(key, jsonVlaue, ttl, TimeUnit.SECONDS);

        } catch (Exception e) {
            log.error("Exception",e);

        }
    }
}
