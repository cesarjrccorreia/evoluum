package com.evoluum.cesar.service;

import com.evoluum.cesar.dto.CityDTO;

public interface ICityService 
{	
	public abstract CityDTO[] findAllByStateId(int id);
	
}
