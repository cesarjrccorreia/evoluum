package com.evoluum.cesar.config;

import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Configuration;



/**
 * The Circuit Breaker Config is responsible to enable the Circuit
 * breaker module, which provides a way to to gracefully degrade functionality 
 * when a method call fails.
 * 
 * @author cesar
 *
 */
@Configuration
@EnableCircuitBreaker
public class CircuitBreakerConfig 
{
}
