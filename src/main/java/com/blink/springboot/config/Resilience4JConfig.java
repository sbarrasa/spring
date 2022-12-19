package com.blink.springboot.config;

import java.time.Duration;

import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.core.registry.EntryAddedEvent;
import io.github.resilience4j.core.registry.EntryRemovedEvent;
import io.github.resilience4j.core.registry.EntryReplacedEvent;
import io.github.resilience4j.core.registry.RegistryEventConsumer;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class Resilience4JConfig {
	public static CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
		    .failureRateThreshold(50)
		    .waitDurationInOpenState(Duration.ofMillis(1000))
		    .permittedNumberOfCallsInHalfOpenState(2)
		    .slidingWindowSize(2)
		    .build();

		// Create a CircuitBreakerRegistry with a custom global configuration
		private TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(4))
													.build();

	@Bean
	public Customizer<Resilience4JCircuitBreakerFactory> globalCustomConfiguration() {

		return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
												.timeLimiterConfig(timeLimiterConfig)
												.circuitBreakerConfig(circuitBreakerConfig)
												.build());
	}
	
}