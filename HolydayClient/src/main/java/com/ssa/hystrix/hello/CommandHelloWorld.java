package com.ssa.hystrix.hello;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public abstract class CommandHelloWorld extends HystrixCommand<String>{

	
	private static String hystrixCommandGroupKey = "ExampleGroup";
	
	private final String name;
	private String url = "http://localhost:8080/api/timeline/";
	private int delaySec = 1;
	private int id =1;
	
	
	  public CommandHelloWorld(String name, int miliSec) {
	      super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(hystrixCommandGroupKey))
	              .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
	            		  
	            		  
	                      //This property determines whether a circuit breaker will be used 
	                      //to track health and to short-circuit requests if it trips.
	                      .withCircuitBreakerEnabled(true)
	                      
	                      
	                      /*
	                       * This property sets the minimum number of requests in a rolling window 
	                       * that will trip the circuit.
	                       * */
	                      .withCircuitBreakerRequestVolumeThreshold(5)
	                      
	                      /*
	                       * the amount of time, after tripping the circuit, 
	                       * to reject requests before allowing attempts again to determine 
	                       * if the circuit should again be closed*/
	                      .withCircuitBreakerSleepWindowInMilliseconds(5*1000) //5 sec
	                      
	                      /*
	                       * his property, if true, forces the circuit breaker into a closed state 
	                       * in which it will allow requests regardless of the error percentage.*/
	                      //.withCircuitBreakerForceClosed(true)
	                      
	                      
	                     .withExecutionTimeoutInMilliseconds(miliSec)));
	      this.name = name;
	    }
    
	  //hystrix.command.HystrixCommandKey.execution.isolation.thread.timeoutInMilliseconds
    public CommandHelloWorld(String name, int miliSec, int id) {
      super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(hystrixCommandGroupKey))
              .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
            		  
            		  
                      //This property determines whether a circuit breaker will be used 
                      //to track health and to short-circuit requests if it trips.
                      .withCircuitBreakerEnabled(true)
                      
                      
                      /*
                       * This property sets the minimum number of requests in a rolling window 
                       * that will trip the circuit.
                       * */
                      .withCircuitBreakerRequestVolumeThreshold(3)
                      
                      /*
                       * the amount of time, after tripping the circuit, 
                       * to reject requests before allowing attempts again to determine 
                       * if the circuit should again be closed*/
                      .withCircuitBreakerSleepWindowInMilliseconds(5000) //5 sec
                      
                      /*
                       * his property, if true, forces the circuit breaker into a closed state 
                       * in which it will allow requests regardless of the error percentage.*/
                      //.withCircuitBreakerForceClosed(true)
                      
                      .withCircuitBreakerErrorThresholdPercentage(50)
                       
                     .withExecutionTimeoutInMilliseconds(miliSec)));
      this.name = name;
      this.id = id;
      
     // hystrix.command.CommandHelloWorld.execution.isolation.thread.timeoutInMilliseconds
    }

    public CommandHelloWorld(String name) {
    	
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(hystrixCommandGroupKey))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                       .withExecutionTimeoutInMilliseconds(500)
                      
                       //This property determines whether a circuit breaker will be used 
                       //to track health and to short-circuit requests if it trips.
                       .withCircuitBreakerEnabled(true)
                       /*
                        * This property sets the minimum number of requests in a rolling window 
                        * that will trip the circuit.
                        * */
                       .withCircuitBreakerRequestVolumeThreshold(5)
                       /*
                        * the amount of time, after tripping the circuit, 
                        * to reject requests before allowing attempts again to determine 
                        * if the circuit should again be closed*/
                       .withCircuitBreakerSleepWindowInMilliseconds(1*1000) //5 sec
                       /*
                        * his property, if true, forces the circuit breaker into a closed state 
                        * in which it will allow requests regardless of the error percentage.*/
                       .withCircuitBreakerForceClosed(true)
                       
                		
                		));
        this.name = name;
      }

    
    
    
    
    @Override
    protected String run() {
        // a real example would do work like a network call here

		String addr = this.url + id;

		System.out.println(name + "| run method called : url=" + addr);
		try{
		HttpResponse<JsonNode> jsonResponse = Unirest.get(addr)
				  .header("accept", "application/json")
				  .queryString("delay", id)
				  .asJson()
				  .fallback(addr)
				  .fallback(addr)
				  .fallback(addr)
				  .fallback(addr)
				  .fallback(addr)
				  .fallback(addr)
				  .fallback(addr)
				  .fallback(addr)
				  .fallback(addr)
				  .fallback(addr)
				  .fallback(addr)
				  .fallback(addr);

		System.out.println("Response:"+name + " | " +  jsonResponse.getBody());
				
				
		}catch(Exception e){
			e.printStackTrace();
		}
		
    	
        return "Hello " + name + "!";
    }
    
    
    
    abstract String getFallback1();
    
    @Override
    protected String getFallback() {
    	System.out.println("##" + name + " ## Fall back called!!");
    	
    	return super.getFallback();
    }

	public int getDelaySec() {
		return delaySec;
	}

	public void setDelaySec(int delaySec) {
		this.delaySec = delaySec;
	}
}
