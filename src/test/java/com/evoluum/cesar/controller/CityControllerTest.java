package com.evoluum.cesar.controller;

import static com.evoluum.cesar.constants.ApiLinksV1.CITIES;
import static com.evoluum.cesar.constants.ApiLinksV1.CITIES_CSV;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.evoluum.cesar.AbstractSpringIntegration;
import com.evoluum.cesar.model.CidadeCsvDataFormat;
import com.evoluum.cesar.model.City;
import com.fasterxml.jackson.core.JsonProcessingException;


public class CityControllerTest extends AbstractSpringIntegration 
{
	
	@Test
	public void whenTryFindAll_ShouldReturncityArray() throws Exception 
	{		
	    mvc.perform(MockMvcRequestBuilders.get(CITIES))
	    	.andExpect(status().isOk())
	    	.andExpect(jsonPath("$").isArray())
	    	.andExpect(jsonPath("$.length()").isNumber());
	}
	
	@Test
	public void whenTryFindAllInCsvFormat_ShouldReturnCsv() throws Exception 
	{		
	    mvc.perform(MockMvcRequestBuilders.get(CITIES_CSV))
	    	.andExpect(status().isOk());
	}
	
	@Test
	public void whenTryFindAcity_ShouldReturnId() throws Exception 
	{		
	    mvc.perform(MockMvcRequestBuilders.get(CITIES + "/Salvador"))
	    	.andExpect(status().isOk());
	}
	
	@Test
	public void whenTryFindAWrongcity_ShouldReturnNotFound() throws Exception 
	{		
	    mvc.perform(MockMvcRequestBuilders.get(CITIES + "/Salvador2"))
	    	.andExpect(status().isNotFound());
	}
	
	@Test
	public void whenGetDataWithEmptycityArray_ShouldReturnJustHeader() throws JsonProcessingException 
	{
		List<City> citys = new ArrayList<City>();
		CidadeCsvDataFormat dataFormat = new CidadeCsvDataFormat(citys);
		
		byte[] data = dataFormat.getData();
		int toCompare = "idEstado,siglaEstado,regiaoNome,nomeCidade,nomeMesorregiao,nomeFormatado".getBytes().length;
		assertThat(data.length, is(lessThanOrEqualTo(toCompare)));
	}
}
