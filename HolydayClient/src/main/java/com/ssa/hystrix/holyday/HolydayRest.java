package com.ssa.hystrix.holyday;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.ssa.hystrix.hello.CommandHelloWorld;

public class HolydayRest extends HystrixCommand<String>{

	private static String hystrixCommandGroupKey = "ExampleGroup";
	
	private static String name = "sample";
	private static int delaySec = 10;
	private static HolydayRest holydayRest;
	private String url;
	private static HolydayConfig config;
	
	public static HolydayRest getInstance() {
		holydayRest = new HolydayRest(name);
        return holydayRest;
    }
	
	public static HolydayRest getInstance(HolydayConfig configParam) {
		config = configParam;
		
		holydayRest = new HolydayRest(name);
        return holydayRest;
    }
	
	protected HolydayRest(String name) {
		  super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(hystrixCommandGroupKey))
	        		.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
	        				.withExecutionTimeoutInMilliseconds(config.getTimeoutLimit())));
	        this.name = name;
	}
	
	

	@Override
	protected String run() throws Exception {
		
		//일단 get만 있음.
		HttpResponse<JsonNode> jsonResponse = null;
		System.out.println("run method called : url=" + url);

			jsonResponse = Unirest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", delaySec)
				  .asJson();

		System.out.println(jsonResponse.getBody());
		
		return name;
	}
	
	
	public static GetRequest get(String url) {
		return Unirest.get(url);
	}
	
	public String getRs(String url) throws Exception {
		this.url = url;
		holydayRest.execute();
		return name;
	}
	
   @Override
    protected String getFallback() {
	   System.out.println("Fall back called!!");
	   
	   
	   return super.getFallback();
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

	public static void setDelaySec(int delaySec) {
		HolydayRest.delaySec = delaySec;
	}

}
