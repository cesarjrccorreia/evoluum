package com.evoluum.cesar.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evoluum.cesar.dto.StateDTO;
import com.evoluum.cesar.dto.CityDTO;
import com.evoluum.cesar.exception.NotFoundCityException;
import com.evoluum.cesar.model.CidadeAdapter;
import com.evoluum.cesar.model.City;

/**
 * The ibge Service is responsible to implements all routines about ibge
 * 
 * 
 * @author cesar
 *
 */
@Service
public class IbgeService implements IIbgeService 
{
	private static final Logger LOG = LoggerFactory.getLogger(IbgeService.class);
	
	@Autowired
	private IStateService stateService;
	
	@Autowired
	private ICityService cityService;
	
	public List<City> findAllCities() 
	{		
		LOG.info("Try to find all cities");
		List<City> citys = new ArrayList<>();
		
		StateDTO[] statesDTO = stateService.findAll();
		LOG.debug(String.format("Amount of states found: %d", statesDTO.length));
		
		for (StateDTO stateDTO : statesDTO) 
		{
			int id = stateDTO.getId();
			CityDTO[] citiesDTO = cityService.findAllByStateId(id);
			LOG.debug(String.format("Amount of cities found: %d", citiesDTO.length));
			
			for (CityDTO cityDTO : citiesDTO) 
			{
				CidadeAdapter adapter = new CidadeAdapter(cityDTO);
				City city = adapter.getcity();
				citys.add(city);
				LOG.debug(String.format("city added: %s", city));
			}
			
			LOG.info(String.format("Added %d citiess to the list by UF id: %d", citiesDTO.length, id));
		}

		LOG.info(String.format("Total amount of cities added to list: %d", citys.size()));
		
		return citys;
	}

	public int findCityIdByName(String cityName) 
	{
		LOG.info(String.format("Try to find a city by name '%s'", cityName));
		
		StateDTO[] statesDTO = stateService.findAll();
		LOG.debug(String.format("Amount of states found: %d", statesDTO.length));
		
		for (StateDTO stateDTO : statesDTO) 
		{
			int id = stateDTO.getId();
			CityDTO[] citiesDTO = cityService.findAllByStateId(id);
			LOG.debug(String.format("Amount of cities found: %d", citiesDTO.length));
			
			for (CityDTO cityDTO : citiesDTO) 
			{
				if (cityName.equals(cityDTO.getNome())) 
				{
					int cityId = cityDTO.getId();
					LOG.info(String.format("The city '%s' has been found with id %d", cityName, cityId));
					
					return cityId;
				}
			}
		}
		
		String message = String.format("Not found city by name '%s'", cityName);
		LOG.info(message);
		throw new NotFoundCityException(message);
	}
}
