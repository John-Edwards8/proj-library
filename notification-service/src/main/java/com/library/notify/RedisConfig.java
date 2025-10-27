package com.library.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.PatternTopic;

@Configuration
public class RedisConfig {
    @Autowired
    private Environment env;

    @Bean
    RedisConnectionFactory redisConnectionFactory() {
        String host = env.getProperty("spring.data.redis.host", "redis");
        int port = Integer.parseInt(env.getProperty("spring.data.redis.port", "6379"));
        LettuceConnectionFactory factory = new LettuceConnectionFactory(host, port);
        factory.afterPropertiesSet();
        return factory;
    }
    
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory factory,
                                            MessageListener listener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(factory);
        container.addMessageListener(listener, new PatternTopic("order_created"));
        return container;
    }
}
