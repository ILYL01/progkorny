package com.ilyl01.weather;

import java.io.IOException;
import java.net.URL;

import javax.activation.URLDataSource;

import org.apache.commons.io.IOUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Helper class for communicating with the webservice based on location.
 *
 */
public class ServiceHelper {
	/**
	 * Logger.
	 */
	private final static Logger LOG = LoggerFactory.getLogger(ServiceHelper.class);

	/**
	 * Fetches the answer of the webservice in JSON format.
	 *
	 * @see org.apache.commons.io.IOUtils
	 * @see javax.activation.URLDataSource
	 *
	 * @param url The service URL
	 * @return The JSON object
	 * @throws IOException When anything happens with URLDataSource
	 *
	 */
    public static String fetchJSONString(final String url) throws IOException{
    	LOG.trace("trying to fetch JSON answer from service");
    	URLDataSource source = new URLDataSource( new URL(url) );

   		return IOUtils.toString(source.getInputStream());
    }

	/**
	 * Fetches the answer of the webservice in JSON format.
	 *
	 * @see org.apache.commons.io.IOUtils
	 * @see javax.activation.URLDataSource
	 *
	 * @param uds An URLDataSource instance to read up
	 * @return The JSON object
	 * @throws IOException When anything happens with URLDataSource
	 *
	 */
    public static String fetchJSONString(final URLDataSource uds) throws IOException{
    	LOG.trace("trying to fetch JSON answer from service");
    	return IOUtils.toString(uds.getInputStream());
    }

    /**
     * Generates the URL of the webservice call based on location.
     *
     * @see com.ilyl01.weather.URLBuilder
     *
     * @param location The location, f.e. name of city
     * @return The URL of the webservice
     */
    public static String generateServiceURL(final String location){
    	LOG.trace("generating service URL for location: "+location);
    	return new URLBuilder("http://api.openweathermap.org/data/2.5/weather")
    			   .addQueryParam("q", location)
    			   .addQueryParam("mode", "json")
    			   .addQueryParam("units", "metric")
    			   .getUrl();
    }
}

