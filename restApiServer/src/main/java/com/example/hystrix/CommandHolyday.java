package com.example.hystrix;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.mashape.unirest.request.GetRequest;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public abstract class CommandHolyday extends HystrixCommand<String>{
	
	private final String url;
	public GetRequest response;
	private static String getHystrixGroupKey(String url){
		try {
		MessageDigest md;
			md = MessageDigest.getInstance("MD5");
		md.update(url.getBytes()); 
		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer(); 
		for(int i = 0 ; i < byteData.length ; i++){
			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return url;
		}
	}
	
	public CommandHolyday(String url) {
		
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(getHystrixGroupKey(url)))
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
	                     .withExecutionTimeoutInMilliseconds(2*1000)));
        this.url = url;
        System.out.println("Contructor called!!!");
	}
	

    @Override
    protected String getFallback() {
    	System.out.println("##" + url + " ## Fall back called!!");
    	holydayFallback();
    	return super.getFallback();
    }
    public abstract void holydayFallback();


	@Override
	protected String run() throws Exception {
		System.out.println("## holydayRun(); before");
		holydayRun();
		//new GetRequest(HttpMethod.GET, url);
		
		System.out.println("## holydayRun(); after");
		//call();
  	return url + " called!!";
	}

	/*public void call() throws UnirestException {
		HttpResponse<JsonNode> reponse = HolydayRest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", 3)
				  .asJson();
  	System.out.println(reponse.getBody());
	}*/
	
	public abstract void holydayRun() throws Exception;
	
	
}
