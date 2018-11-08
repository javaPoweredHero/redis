package com.test.task.redis;

import com.test.task.dal.domain.Car;
import com.test.task.dal.domain.Truck;
import com.test.task.services.impl.RedisContainer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisContainersConfiguration {

    @Bean
    RedisContainer<Car> carContainer(@Qualifier("redis-template") RedisTemplate<Object, Object> redisTemplate) {
        return new RedisContainer<>(Car.class, redisTemplate);
    }

    @Bean
    RedisContainer<Truck> truckContainer(@Qualifier("redis-template") RedisTemplate<Object, Object> redisTemplate) {
        return new RedisContainer<>(Truck.class, redisTemplate);
    }
}
