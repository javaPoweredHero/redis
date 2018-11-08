package com.test.task.redis;

import com.test.task.TaskApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfiguration {

    @Autowired private TaskApplicationConfig applicationConfig;

    @Bean
    public RedisClusterConfiguration getClusterConfig() {
        RedisClusterConfiguration rcc = new RedisClusterConfiguration(applicationConfig.getNodes());
        rcc.setMaxRedirects(applicationConfig.getMaxRedirects());
        return rcc;
    }

    @Bean
    @Qualifier("jedis-connection-factory")
    public JedisConnectionFactory getConnectionFactory(RedisClusterConfiguration cluster) {
        return new JedisConnectionFactory(cluster);
    }

    @Bean
    @Qualifier("redis-template")
    RedisTemplate<Object, Object> redisTemplate(@Qualifier("jedis-connection-factory") RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.getConnectionFactory();
        return template;
    }
}
