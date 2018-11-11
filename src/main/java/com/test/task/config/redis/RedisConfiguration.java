package com.test.task.config.redis;

import com.test.task.config.TaskApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class RedisConfiguration {

    @Autowired
    private TaskApplicationConfig applicationConfig;

    @Bean
    public RedisClusterConfiguration getClusterConfig() {
        return new RedisClusterConfiguration(applicationConfig.getNodes());
    }

    @Bean
    @Qualifier("jedis-connection-factory")
    public JedisConnectionFactory getConnectionFactory(RedisClusterConfiguration cluster) {
        return new JedisConnectionFactory(cluster);
    }

    @Bean
    @Qualifier("redis-template")
    RedisTemplate<Object, Object> redisTemplate(
            @Qualifier("jedis-connection-factory") JedisConnectionFactory connectionFactory
    ) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
