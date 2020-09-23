package com.evoluum.cesar.service;

import java.util.List;

import com.evoluum.cesar.model.City;

public interface IIbgeService
{
	public abstract List<City> findAllCities();	
	public abstract int findCityIdByName(String name);
}
