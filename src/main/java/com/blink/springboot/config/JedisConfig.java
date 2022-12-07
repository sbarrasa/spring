package com.blink.springboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
public class JedisConfig {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${spring.redis.database}") Integer database;
	@Value("${spring.redis.port}") Integer port;
    @Value("${spring.redis.host}") String host;
	@Bean
    JedisConnectionFactory jedisConnectionFactory() {

	  logger.info("Redis .database:{} .host:{} .port:{} ",database, host, port);

	  JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
	  redisConnectionFactory.setDatabase(database);
	  redisConnectionFactory.setHostName(host);
	  redisConnectionFactory.setPort(port);
	  return redisConnectionFactory;
   
	}

	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
	    RedisTemplate<String, Object> template = new RedisTemplate<>();
	    template.setConnectionFactory(jedisConnectionFactory());
	    
	    return template;
	}
	
 
 
}