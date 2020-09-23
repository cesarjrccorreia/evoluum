package com.evoluum.cesar.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.springframework.cache.interceptor.SimpleKey.EMPTY;

import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.evoluum.cesar.dto.StateDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * The State Service is responsible to call the IBGE API and returns a 
 * parsed response.
 * 
 * @author cesar
 *
 */
@Component
public class StateService implements IStateService 
{
	private static final Logger LOG = LoggerFactory.getLogger(StateService.class);
	private static final String URL_IBGE = "https://servicodados.ibge.gov.br/api/v1/localidades/estados";
	
	@Autowired
	private CacheManager cacheManager;

	@CachePut("statesCache")
	@HystrixCommand(fallbackMethod = "findAllFallback")
	public StateDTO[] findAll() 
	{
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build());
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		
		ResponseEntity<StateDTO[]> response = restTemplate.getForEntity(URL_IBGE, StateDTO[].class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		
		StateDTO[] body = response.getBody();
		assertThat(body.length, is(not(0)));
		
		return body;
	}
	
	@HystrixCommand
	private StateDTO[] findAllFallback() 
	{
		Cache cache = cacheManager.getCache("statesCache");
		
		if (cache == null || cache.get(EMPTY) == null)
		{
			LOG.warn("Circuit breaker detected and no data in cache, data may be inconsistent");
			return new StateDTO[0];
		}
		
		LOG.warn("Circuit breaker detected");
		return cache.get(EMPTY, StateDTO[].class);
	}
}
