package com.ssa.hystrix.holyday;

import java.util.Map;

import org.omg.CORBA.RepositoryIdHelper;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.ssa.hystrix.hello.CommandHolydayEdited;

public class HolydayRestClient {
	
	private CommandHolydayEdited command;
	
	
	public HolydayRestClient(final String url) {
		this.command = new CommandHolydayEdited(url, new GetRequest(HttpMethod.GET, url)) {
			
			@Override
			public void holydayRun() throws Exception{
				this.request.asJson();
				
			}
			
			@Override
			public void holydayFallback() {
				//DO NOTHING
			}
		};
	}
	
	private String url;
	private String httpRequestMethod;
	private Map<String, String> httpRequestHeaders;
	private Map<String, String> queryString;
	private Map<String, String> cookies;
	
	/**
	 * set HTTP METHOD
	 * 
	 * @param method
	 * @return RequestSettings
	 */
	public HolydayRestClient method(String method) {
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
	public HolydayRestClient header(String key, String value) {
		if((key != null && !"".equals(key)) && (value != null && !"".equals(value))) {
			
//			System.out.println(command.request);
			
			this.command.request.header(key, value);
			
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
	public HolydayRestClient headers(Map<String, String> headers) {
		if(!headers.isEmpty()) {
			for(String key : headers.keySet()) {
				this.command.request.header(key, headers.get(key));
			}
			
		} else {
			
			throw new IllegalArgumentException();
		}
		
		return this;
	}
	
	/**
	 * 
	 * set HTTP query string
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public HolydayRestClient queryString(String key, Object value) {
		if((key != null && !"".equals(key)) && (value != null)) {
			
			this.command.request.queryString(key, value);
			
		} else {
			
			throw new IllegalArgumentException();
		}
		
		return this;
	}
	
	public HttpResponse<JsonNode> sendRequest() {
		
		//TODO: asJson => other things
		HttpResponse<JsonNode> response = null;
		
		try {
			
			this.command.execute();
//			response = this.command.request.asJson();
			
			System.out.println(response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	/**
	 * set HTTP request cookie
	 * 
	 * @param key
	 * @param value
	 * @return RequestSettings
	 */
//	public HolydayRestClient cookie(String key, String value) {
//		if((key != null && !"".equals(key)) && (value != null && !"".equals(value))) {
//			
//			this.cookies.put(key, value);
//			
//		} else {
//			
//			throw new IllegalArgumentException();
//		}
//		
//		return this;
//	}
	
	/**
	 * set HTTP request cookies
	 * 
	 * @param cookies
	 * @return RequestSettings
	 */
//	public HolydayRestClient cookies(Map<String, String> cookies) {
//		if(!cookies.isEmpty()) {
//			for(String key : cookies.keySet()) {
//				this.cookies.put(key, cookies.get(key));
//			}
//			
//		} else {
//			
//			throw new IllegalArgumentException();
//		}
//		
//		return this;
//	}
}
