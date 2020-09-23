package com.evoluum.cesar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MesorRegionDTO extends BaseDTO 
{

	@JsonProperty("UF")
	private StateDTO estado;

	public StateDTO getEstado() 
	{
		return estado;
	}

	public void setEstado(StateDTO estado)
	{
		this.estado = estado;
	}
	
	
}
