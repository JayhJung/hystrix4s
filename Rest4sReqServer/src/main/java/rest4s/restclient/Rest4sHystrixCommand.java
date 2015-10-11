package rest4s.restclient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import rest4s.config.RequestType;
import rest4s.config.Rest4sHystrixConfiguration;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

public class Rest4sHystrixCommand extends HystrixCommand<String>{

	private String url;
	private RequestType reqType;
	Class noparams[] = {};
	private Rest4sHystrixConfiguration conf= null;
	
	public Rest4sHystrixCommand(String url, RequestType reqType) {
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
      this.reqType = reqType;
//      System.out.println("Contructor called!!!");
	}
	
	public Rest4sHystrixCommand(String url, RequestType reqType, Rest4sHystrixConfiguration conf) {
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
	                     .withExecutionTimeoutInMilliseconds(conf.getTimeOutSec()*1000)));
      this.url = url;
      this.reqType = reqType;
      this.conf = conf;
      System.out.println(conf.toString());
	}
	
	
	@Override
	protected String run() throws Exception {
		switch (reqType) {
		case GET:
			return HttpClientCall.GetRequest(url);
		case POST:
			return HttpClientCall.PostRequest(url);
		default:
			return null;
		}
	}
	
	
	@Override
	protected String getFallback() {
		if(this.conf.getFallbackMethodName() == null || "".equals(this.conf.getFallbackMethodName())){
			System.out.println("no fallback specified");
			return "no fallback specified";
		}else{
			System.out.println("fallback method name : " + this.conf.getFallbackMethodName());
			try {
				Method method = conf.getCallerClass().getMethod(conf.getFallbackMethodName(),noparams);
				method.invoke(conf.getCallerClass().newInstance(), null);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally{
				return "fall back called";
			}
		}
		
	}
	
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

}
