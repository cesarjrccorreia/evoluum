package com.evoluum.cesar.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The Caching Config is responsible to enable the Caching module, which 
 * provides a way to improve the performance of the system.
 * 
 * @author cesar
 *
 */
@Configuration
@EnableCaching
public class CachingConfig 
{	
	@Bean
	public CacheManager cacheManager() 
	{
	    SimpleCacheManager cacheManager = new SimpleCacheManager();
	    List<Cache> caches = new ArrayList<Cache>();
	    caches.add(new ConcurrentMapCache("findcityByName"));
	    caches.add(new ConcurrentMapCache("statesCache"));
	    caches.add(new ConcurrentMapCache("citiesCache"));
	    cacheManager.setCaches(caches);
	    
	    return cacheManager;
	}
}
