package com.evoluum.cesar.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

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

import com.evoluum.cesar.dto.CityDTO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * The city service implements is responsible to call the 
 * IBGE API and returns a parsed response.
 * 
 * @author cesar
 *
 */
@Component
public class CityService implements ICityService
{	
	private static final Logger LOG = LoggerFactory.getLogger(CityService.class);
	private static final String URL = "https://servicodados.ibge.gov.br/api/v1/localidades/estados/%s/municipios";

	@Autowired
	private CacheManager cacheManager;

	@CachePut(value = "citiesCache", key = "#stateId")
	@HystrixCommand(fallbackMethod = "findAllByStateFallback")
	public CityDTO[] findAllByStateId(int stateId) 
	{
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(HttpClientBuilder.create().build());
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);
		String resource = String.format(URL, stateId);

		ResponseEntity<CityDTO[]> response = restTemplate.getForEntity(resource, CityDTO[].class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));

		CityDTO[] body = response.getBody();
		assertThat(body.length, is(not(0)));
		
		return body;
	}

	@HystrixCommand
	private CityDTO[] findAllByStateFallback(int stateId) 
	{
		Cache cache = cacheManager.getCache("citiesCache");
		
		if (cache == null || cache.get(stateId) == null) 
		{
			LOG.warn("Circuit breaker detected and no data in cache, data may be inconsistent");
			return new CityDTO[0];
		}
		
		LOG.warn("Circuit breaker detected");
		return cache.get(stateId, CityDTO[].class);
	}
}
