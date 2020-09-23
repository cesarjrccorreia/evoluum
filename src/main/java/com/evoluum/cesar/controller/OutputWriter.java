package com.evoluum.cesar.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

/**
 * The Output Writer is responsible to write a stream of data to {@link HttpServletResponse}
 * 
 * @author cesar
 *
 */
class OutputWriter 
{	
	public void writeDataToHttpResponse(byte[] data, HttpServletResponse response) throws IOException 
	{
		byte[] buffer = new byte[1024 * 8];
		try (InputStream input = new ByteArrayInputStream(data); OutputStream output = response.getOutputStream();) 
		{
			for (int length = 0; (length = input.read(buffer)) > 0;)
			{
				output.write(buffer, 0, length);
			}
		}
	}
}