package com.zainab.journalApp.service;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;

@SpringBootTest
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    public void redisTest(){
       // redisTemplate.opsForValue().set("email","name@gmail.com");
        Object salary = redisTemplate.opsForValue().get("salary");
        System.out.println(salary);
        int a=1;
    }
}
