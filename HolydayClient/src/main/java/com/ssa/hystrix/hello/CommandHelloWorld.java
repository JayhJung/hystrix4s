package com.ssa.hystrix.hello;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class CommandHelloWorld extends HystrixCommand<String>{

	
	private static String hystrixCommandGroupKey = "ExampleGroup";
	
	private final String name;
	private String url = "http://localhost:8080/api/timeline/";
	private int delaySec = 0;
	
	
    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey(hystrixCommandGroupKey));
        this.name = name;
    }
    
    public CommandHelloWorld(String name, int delayLimitSec) {
      super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(hystrixCommandGroupKey))
              .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                     .withExecutionTimeoutInMilliseconds(delayLimitSec)));
      this.name = name;
    }

    public void setCallUrl(String url){
    	this.url = url;
    }
    
    public void setDelay(int sec){
    	this.delaySec = sec;
    }
    
    
    @Override
    protected String run() {
        // a real example would do work like a network call here

		String addr = this.url;

		System.out.println("run method called : url=" + addr);
		try{
		HttpResponse<JsonNode> jsonResponse = Unirest.get(addr)
				  .header("accept", "application/json")
				  .queryString("delay", delaySec)
				  .asJson();

		System.out.println(jsonResponse.getBody());
				
				
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
