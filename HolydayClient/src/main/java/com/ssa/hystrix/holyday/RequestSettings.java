package com.ssa.holyday.restclient;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RequestSettings {
	
	private String url;
	private String httpRequestMethod;
	private Map<String, String> httpRequestHeaders;
	private Map<String, String> queryString;
	private Map<String, String> cookies;
	
	/**
	 * Request Settings Class Constructor
	 * 
	 * @param url
	 */
	public RequestSettings(String url) {
		
		if(url != null && !"".equals(url)) {
			
			String urlRegex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			Pattern pattern = Pattern.compile(urlRegex);
            Matcher matcher = pattern.matcher(url);
            if(matcher.matches()) {
            	
            	this.url = url;
            	
            } else {
    			
    			throw new IllegalArgumentException();
            }
			
		} else {
			
			throw new IllegalArgumentException();
		}
		
		// defaults
		this.httpRequestMethod = "get";
		
	}
	
	/**
	 * set HTTP METHOD
	 * 
	 * @param method
	 * @return RequestSettings
	 */
	public RequestSettings method(String method) {
		if(method != null && !"".equals(method)) {
			
			if(method.equalsIgnoreCase("get") || method.equalsIgnoreCase("head") ||
					method.equalsIgnoreCase("post") || method.equalsIgnoreCase("put") ||
					method.equalsIgnoreCase("delete") || method.equalsIgnoreCase("options") ||
					method.equalsIgnoreCase("options")) {
				
				this.httpRequestMethod = method.toLowerCase();
			} else {
				
				throw new IllegalArgumentException();
			}
		} else {
			
			throw new IllegalArgumentException();
		}
		
		return this;
	}
	
	/**
	 * set HTTP request header
	 * 
	 * @param key
	 * @param value
	 * @return RequestSettings
	 */
	public RequestSettings header(String key, String value) {
		if((key != null && !"".equals(key)) && (value != null && !"".equals(value))) {
			
			this.httpRequestHeaders.put(key, value);
			
		} else {
			
			throw new IllegalArgumentException();
		}
		
		return this;
	}
	
	/**
	 * set HTTP request headers
	 * 
	 * @param headers
	 * @return RequestSettings
	 */
	public RequestSettings headers(Map<String, String> headers) {
		if(!headers.isEmpty()) {
			for(String key : headers.keySet()) {
				this.httpRequestHeaders.put(key, headers.get(key));
			}
			
		} else {
			
			throw new IllegalArgumentException();
		}
		
		return this;
	}
	
	/**
	 * set HTTP request cookie
	 * 
	 * @param key
	 * @param value
	 * @return RequestSettings
	 */
	public RequestSettings cookie(String key, String value) {
		if((key != null && !"".equals(key)) && (value != null && !"".equals(value))) {
			
			this.cookies.put(key, value);
			
		} else {
			
			throw new IllegalArgumentException();
		}
		
		return this;
	}
	
	/**
	 * set HTTP request cookies
	 * 
	 * @param cookies
	 * @return RequestSettings
	 */
	public RequestSettings cookies(Map<String, String> cookies) {
		if(!cookies.isEmpty()) {
			for(String key : cookies.keySet()) {
				this.cookies.put(key, cookies.get(key));
			}
			
		} else {
			
			throw new IllegalArgumentException();
		}
		
		return this;
	}
}
