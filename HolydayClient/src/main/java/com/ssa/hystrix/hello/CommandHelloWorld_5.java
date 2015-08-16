package com.ssa.hystrix.hello;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class CommandHelloWorld_5 extends HystrixCommand<String>{

	private final String name;

    public CommandHelloWorld_5(String name) {
//        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
      
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                       .withExecutionTimeoutInMilliseconds(10000)));
        this.name = name;
        
    }

    @Override
    protected String run() {
        // a real example would do work like a network call here

		String addr = "http://localhost:8080/api/timeline/5";
		String addr2 = "http://httpbin.org/post";
		
		try{
		HttpResponse<JsonNode> jsonResponse = Unirest.get(addr)
				  .header("accept", "application/json")
				  .queryString("apiKey", "123")
				  .asJson();
		
		
		//http://192.168.0.19:8080/api/timeline/
		System.out.println("Client 5sec :" + jsonResponse.getBody());
				
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
    	
        return "Hello " + name + "!";
    }
    
    
    @Override
    protected String getFallback() {
    	System.out.println("fall back called!!");
    	//다시 호출해야 하나?
    	
    	return super.getFallback();
    }
}
