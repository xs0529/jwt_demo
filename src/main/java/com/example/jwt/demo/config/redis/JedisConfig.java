package com.example.jwt.demo.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author: 谢霜
 * @Date: 2018/09/14 下午 17:38
 * @Description:
 */
@Configuration
public class JedisConfig {

    @Bean
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        return new JedisPool(jedisPoolConfig, "127.0.0.1", 6379, 5000, null);
    }
}
