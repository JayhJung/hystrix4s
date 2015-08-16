package com.ssa.hystrix.hello;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class CommandSimple extends HystrixCommand<String>{

	private final String name;

    public CommandSimple(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }
    
    @Override
    protected String run() {
    	
    	String addr = "http://localhost:8080/api/timeline/";
    	//throw new RuntimeException("blocked");
        // a real example would do work like a network call here
    	try {
    		HttpResponse<JsonNode> jsonResponse = Unirest.get(addr)
  				  .header("accept", "application/json")
  				  .queryString("apiKey", "123")
  				  .asJson();
  		//http://192.168.0.19:8080/api/timeline/
    		System.out.println(jsonResponse.getBody());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "Hello " + name + "!";
    }
    
    @Override
    protected String getCacheKey() {
        return String.valueOf(name);
    }
    
    
    @Override
    protected String getFallback() {
    	return "Good Bye";
    }
}
