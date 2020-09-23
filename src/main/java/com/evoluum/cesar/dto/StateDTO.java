package com.evoluum.cesar.dto;

public class StateDTO extends BaseDTO
{
	private String sigla;
	private RegionDTO regiao;
	
	public StateDTO() 
	{		
	}


	public String getSigla() 
	{
		return sigla;
	}

	public void setSigla(String sigla) 
	{
		this.sigla = sigla;
	}

	public RegionDTO getRegiao() 
	{
		return regiao;
	}

	public void setRegiao(RegionDTO regiao) 
	{
		this.regiao = regiao;
	}
}
