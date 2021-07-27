package com.fastcampus.mobility.config;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600 * 2, redisNamespace = "spring:session")
public class RedisConfig {

    private String host;
    private Integer port;

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(30);
        poolConfig.setMinIdle(2);
        poolConfig.setMaxIdle(5);

        LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .build();
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration(host, port);
        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer serializer = new DefaultCookieSerializer();
        serializer.setCookieName("SPRING-SESSION");
        serializer.setCookiePath("/");
        return serializer;
    }
}
