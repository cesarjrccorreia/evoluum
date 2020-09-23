package com.evoluum.cesar.dto;


public class CityDTO extends BaseDTO
{
	private MicrorRegionDTO microrregiao;

	public MicrorRegionDTO getMicrorregiao() 
	{
		return microrregiao;
	}

	public void setMicrorregiao(MicrorRegionDTO microrregiao) 
	{
		this.microrregiao = microrregiao;
	}
}