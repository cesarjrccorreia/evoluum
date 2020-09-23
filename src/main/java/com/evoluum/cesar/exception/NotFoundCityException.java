package com.evoluum.cesar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundCityException extends RuntimeException
{
	private static final long serialVersionUID = -5077839649131336579L;
	
	public NotFoundCityException(String message) 
	{
		super(message);
	}
}
