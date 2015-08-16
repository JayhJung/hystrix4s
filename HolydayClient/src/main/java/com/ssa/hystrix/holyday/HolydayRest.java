package com.ssa.hystrix.holyday;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class HolydayRest extends HystrixCommand<String>{

	protected HolydayRest(HystrixCommandGroupKey group) {
		super(group);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String run() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public static GetRequest get(String url) {
		return Unirest.get(url);
	}
	
	public static GetRequest head(String url) {
		return Unirest.head(url);
	}
	
	public static HttpRequestWithBody options(String url) {
		return Unirest.options(url);
	}
	
	public static HttpRequestWithBody post(String url) {
		return Unirest.post(url);
	}
	
	public static HttpRequestWithBody delete(String url) {
		return Unirest.delete(url);
	}
	
	public static HttpRequestWithBody patch(String url) {
		return Unirest.patch(url);
	}
	
	public static HttpRequestWithBody put(String url) {
		return Unirest.put(url);
	}

}
