package com.ilyl01.weather;

import java.io.IOException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class.
 *
 */
public class App{
	/**
	 * Logger.
	 */
	private static final Logger LOG = LoggerFactory.getLogger(App.class);

	/**
	 * Main method.
	 *
	 * @param args The arguments
	 */
    public static void main(final String[] args){
    	LOG.trace("main start");

        try{
        	if(args.length<1){
        		System.out.println("Please give a location as first argument");
        		LOG.debug("missing argument: location");
        		return;
        	}

        	// show magic
        	System.out.println(generateOutput(args[0]));

        }
        catch(Exception e){
        	LOG.error(e.getMessage());
        	LOG.warn(e.getMessage(),e);
        }
        finally{
        	LOG.trace("main end");
        }
    }

    /**
     * Generates the output to show.
     *
     * @param location The location, f.e. name of city
     * @return The output
     * @throws Exception General exception
     */
    public static String generateOutput(final String location) throws Exception{
    	LOG.trace("generateOutput start");
    	JSONObject json;
    	try{
    		json = new JSONObject(ServiceHelper.fetchJSONString(ServiceHelper.generateServiceURL(location)));
    	}
    	catch(IOException e){
    		return "The weather service is unavailable. Please try again later.";
    	}

    	if(json.has("message")){
    		LOG.debug("service can't find given location: "+location);
    		return json.getString("message");
    	}
    	JSONObject jsonMain = json.getJSONObject("main");

    	LOG.trace("generating output");
    	StringBuilder s = new StringBuilder("Weather in ");
    	appendString(s, json.getJSONArray("weather").getJSONObject(0), location, "description");
    	appendDouble(s, jsonMain, "Temperature", "temp", "°C");
    	appendDouble(s, jsonMain, "Humidity", "humidity", "%");
    	appendDouble(s, jsonMain, "Pressure", "pressure", "hPa");
    	appendDouble(s, json.getJSONObject("wind"), "Wind speed", "speed", "m/s");

    	LOG.trace("generateOutput end");
    	return s.toString();
    }

    /**
     * Appends a line to a StringBuilder based on a Double value of a JSON object.
     *
     * @param s The StringBuilder instance
     * @param json The JSONObject instance
     * @param displayName The display name, f.e. Temperature
     * @param keyName The key that holds the data in JSON, f.e. temp
     * @param unit A string to be put to after the value, f.e. °C
     */
    public static void appendDouble(final StringBuilder s, final JSONObject json, final String displayName, final String keyName, final String unit){
    	LOG.trace("appending "+displayName);
    	s.append(displayName);
    	s.append(": ");
    	s.append(json.getDouble(keyName));
    	s.append(unit);
    	s.append(System.getProperty("line.separator"));
    	LOG.trace(displayName+" appended successfully");
    }

    /**
     * Appends a line to a StringBuilder based on a String value of a JSON object.
     *
     * @param s The StringBuilder instance
     * @param json The JSONObject instance
     * @param displayName The display name, f.e. Temperature
     * @param keyName The key that holds the data in JSON, f.e. temp
     */
    public static void appendString(final StringBuilder s, final JSONObject json, final String displayName, final String keyName){
    	LOG.trace("appending "+displayName);
    	s.append(displayName);
    	s.append(": ");
    	s.append(json.getString(keyName));
    	s.append(System.getProperty("line.separator"));
    	LOG.trace(displayName+" appended successfully");
    }

}

