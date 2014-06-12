package com.ilyl01.weather;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Tool for working with URLs.
 *
 */
public class URLBuilder {

	/**
	 * URL.
	 */
	private String url;
	/**
	 * Logger.
	 */
	private final static Logger LOG = LoggerFactory.getLogger(URLBuilder.class);

	/**
	 * Constructor.
	 *
	 * @param url The base URL to start with, for example <code>http://example.com/index.php</code>
	 */
	public URLBuilder(final String url) {
		LOG.trace("initiating URL: "+url);
		this.url = url;
	}

	/**
	 * Adds a query parameter to the URL.
	 * <p>Extends the URL with a new query parameter. If the URL already contains one or several query parameters,
	 * the new parameter will be concatenated with <code>&</code>, otherwise with <code>?</code>.</p>
	 * Other features:
	 * <ul>
	 * <li>The parameter value will be URL-encoded.</li>
	 * <li>If no parameter value given, the URL will be extended with only a new empty parameter.</li>
	 * </ul>
	 *
	 * @see java.net.URLEncoder.encode
	 *
	 * @param parameterName The <i>name</i> of the new query parameter
	 * @param parameterValue The <i>value</i> of the new query parameter
	 * @return The URLBuilder object for further chaining
	 */
	public URLBuilder addQueryParam(final String parameterName, final String parameterValue) {
		LOG.trace("adding query param to URL: "+parameterName+"="+parameterValue);
		if(parameterName==null || parameterName.length()<1){
			LOG.info("no parameter name was given");
			return this;
		}
		else{
			try{
				String s = (url.contains("?") ? "&" : "?") + parameterName + ((parameterValue!=null && parameterValue.length()>0) ? "=" + URLEncoder.encode(parameterValue, "UTF-8") : "");
				this.url += s;
			}
			catch(UnsupportedEncodingException e){
				LOG.error("URLEncoder failed to encode with UTF-8");
			}
		}
		LOG.trace("parameter "+parameterName+" added successfully");
		return this;
	}

	/**
	 * Short-hand function of {@link #addQueryParam(String parameterName, String parameterValue)}.
	 * <p>Calls {@link #addQueryParam(String parameterName, String parameterValue)} with <code>null</code> as parameterValue.</p>
	 *
	 * @param parameterName Name of the empty parameter
	 * @return The URLBuilder object for further chaining
	 */
	public URLBuilder addQueryParam(final String parameterName){
		return addQueryParam(parameterName, null);
	}

	/**
	 * Returns the generated URL.
	 * @return The URL
	 */
	public String getUrl() {
		LOG.trace("returning URL: "+url);
		return url;
	}

}

