package com.evoluum.cesar.model;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import com.evoluum.cesar.dto.StateDTO;
import com.evoluum.cesar.dto.MesorRegionDTO;
import com.evoluum.cesar.dto.MicrorRegionDTO;
import com.evoluum.cesar.dto.CityDTO;
import com.evoluum.cesar.dto.RegionDTO;

public class CidadeAdapter 
{

	private CityDTO municipio;

	public CidadeAdapter(CityDTO municipio)
	{
		assertThat(municipio, is(notNullValue()));
		this.municipio = municipio;
	}
	
	public City getcity()
	{
		MicrorRegionDTO microrregiao = municipio.getMicrorregiao();
		MesorRegionDTO mesorregiao = microrregiao.getMesorregiao();
		StateDTO estado = mesorregiao.getEstado();
		RegionDTO regiao = estado.getRegiao();
		
		int idEstado = estado.getId();
		String siglaEstado = estado.getSigla();
		String regiaoNome = regiao.getNome();
		String nomecity = municipio.getNome();
		String nomeMesorregiao = mesorregiao.getNome();
		String uf = estado.getSigla();
		String nomeFormatado = String.format("%s/%s", nomecity, uf);
		
		return new City(idEstado, siglaEstado, regiaoNome, nomecity, nomeMesorregiao, nomeFormatado);
	}
}
