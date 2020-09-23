package com.evoluum.cesar.controller;

import static com.evoluum.cesar.constants.ApiLinksV1.CITIES;
import static com.evoluum.cesar.constants.ApiLinksV1.CITIES_CSV;
import static com.evoluum.cesar.constants.SwaggerStrings.DESC_CITIES;
import static com.evoluum.cesar.constants.SwaggerStrings.DESC_CITIES_BY_NAME;
import static com.evoluum.cesar.constants.SwaggerStrings.DESC_CITIES_CSV;
import static com.evoluum.cesar.constants.SwaggerStrings.MSG_SERVICE_OFFLINE;
import static com.evoluum.cesar.constants.SwaggerStrings.PARAM_CITY_NAME;
import static com.evoluum.cesar.constants.SwaggerStrings.RESP_NOT_FOUND_CITY_BY_NAME;
import static com.evoluum.cesar.constants.SwaggerStrings.RESP_OK_CITY_BY_NAME;
import static com.evoluum.cesar.constants.SwaggerStrings.TAG_CITIES;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.evoluum.cesar.model.CidadeCsvDataFormat;
import com.evoluum.cesar.model.City;
import com.evoluum.cesar.service.IbgeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * The city Rest Controller is responsible to expose endpoints for the city
 * resource
 * 
 * @author cesar
 *
 */
@RestController
@Api(tags = TAG_CITIES)
public class CityController
{
	private static final Logger LOG = LoggerFactory.getLogger(CityController.class);

	@Autowired
	private IbgeService cityService;

	@GetMapping(CITIES)
	@ApiOperation(DESC_CITIES)
	@ApiResponses(value = { @ApiResponse(code = 204, message = MSG_SERVICE_OFFLINE)})
	public ResponseEntity<List<City>> allJson() 
	{
		LOG.info("GET - " + CITIES);

		List<City> cities = cityService.findAllCities();
		
		if(cities.isEmpty()) {
			return new ResponseEntity<List<City>>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<City>>(cities, HttpStatus.OK);
	}

	@GetMapping(value = CITIES_CSV)
	@ApiOperation(DESC_CITIES_CSV)
	@ApiResponses(value = { @ApiResponse(code = 204, message = MSG_SERVICE_OFFLINE) })
	public void allCSV(HttpServletResponse response) throws IOException 
	{
		LOG.info("GET - " + CITIES_CSV);
		
		String filename = "cities.csv";
		response.setContentType("text/csv; charset=utf-8");
		response.setHeader(CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");

		List<City> cities = cityService.findAllCities();

		CidadeCsvDataFormat csvDataFormat = new CidadeCsvDataFormat(cities);
		byte[] data = csvDataFormat.getData();

		OutputWriter outputWriter = new OutputWriter();
		outputWriter.writeDataToHttpResponse(data, response);
	}

	@GetMapping(CITIES + "/{cityName}")
	@ApiOperation(DESC_CITIES_BY_NAME)
	@ApiResponses(value = { 
				@ApiResponse(code = 200, message = RESP_OK_CITY_BY_NAME),
				@ApiResponse(code = 404, message = RESP_NOT_FOUND_CITY_BY_NAME) 
			})
	@Cacheable("findcityByName")
	public int findByName(@ApiParam(PARAM_CITY_NAME) @PathVariable(value = "cityName", required = true) String cityName) 
	{
		LOG.info("GET - " + CITIES + "/" + cityName);

		int id = cityService.findCityIdByName(cityName);

		return id;
	}
}
