package com.evolvestage.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import static com.evolvestage.api.BaseTest.REDIS_PORT;
import static com.evolvestage.api.BaseTest.redis;

@Configuration
public class RedisTestConfig {

    @Bean
    @Primary
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName(redis.getContainerIpAddress());
        configuration.setPort(redis.getMappedPort(REDIS_PORT));
        return new LettuceConnectionFactory(configuration);
    }
}
