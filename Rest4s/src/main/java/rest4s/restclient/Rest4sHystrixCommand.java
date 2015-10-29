package rest4s.restclient;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import rest4s.config.RequestType;
import rest4s.config.Rest4sHystrixConfiguration;

public class Rest4sHystrixCommand extends HystrixCommand<String>{
	
	private static final Logger logger = LoggerFactory.getLogger(Rest4sHystrixCommand.class);

	private String url;
	private RequestType reqType;
//	Class noparams[] = {};
	private Rest4sHystrixConfiguration conf;
	private RequestSettings requestSettings;
	
	public Rest4sHystrixCommand(String url, RequestType reqType) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(getDigestedHystrixGroupKey(url)))
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
	                     .withExecutionTimeoutInMilliseconds(2)));
		this.url = url;
		this.reqType = reqType;
		logger.debug("Contructor called!!!");
	}
	
	public Rest4sHystrixCommand(String url, RequestType reqType, Rest4sHystrixConfiguration conf) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(getDigestedHystrixGroupKey(conf.getGroupKey())))
	              .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
	                      //This property determines whether a circuit breaker will be used 
	                      //to track health and to short-circuit requests if it trips.
	                      .withCircuitBreakerEnabled(true)
	                      /*
	                       * This property sets the minimum number of requests in a rolling window 
	                       * that will trip the circuit.
	                       * */
	                      .withCircuitBreakerRequestVolumeThreshold(3)
	                      /*dk...
	                       * the amount of time, after tripping the circuit, 
	                       * to reject requests before allowing attempts again to determine 
	                       * if the circuit should again be closed*/
	                      .withCircuitBreakerSleepWindowInMilliseconds(5000) //5 sec
	                      /*
	                       * his property, if true, forces the circuit breaker into a closed state 
	                       * in which it will allow requests regardless of the error percentage.*/
	                      //.withCircuitBreakerForceClosed(true)
	                     .withCircuitBreakerErrorThresholdPercentage(50)
	                     .withExecutionTimeoutInMilliseconds(conf.getTimeOutMilliSec())
	                     .withHealthCheckUrl(conf.getHealthCheckUrl())));
		this.url = url;
	    this.reqType = reqType;
	    this.conf = conf;
	    logger.debug(conf.toString());
	}
	
	public Rest4sHystrixCommand(RequestSettings requestSettings, Rest4sHystrixConfiguration conf) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(getDigestedHystrixGroupKey(conf.getGroupKey())))
	              		.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
	              		//This property determines whether a circuit breaker will be used 
	                    //to track health and to short-circuit requests if it trips.
	                    .withCircuitBreakerEnabled(true)
	                    /*
	                     * This property sets the minimum number of requests in a rolling window 
	                     * that will trip the circuit.
	                     * */
	                    .withCircuitBreakerRequestVolumeThreshold(conf.getThreasholdVolume())
	                    /*
	                     * the amount of time, after tripping the circuit, 
	                     * to reject requests before allowing attempts again to determine 
	                     * if the circuit should again be closed*/
	                    .withCircuitBreakerSleepWindowInMilliseconds(conf.getSleepWindowMilliSec()) //5 sec
	                    /*
	                     * his property, if true, forces the circuit breaker into a closed state 
	                     * in which it will allow requests regardless of the error percentage.*/
	                    //.withCircuitBreakerForceClosed(true)
	                    .withCircuitBreakerErrorThresholdPercentage(conf.getThreasholdErrorPercentage())
	                    .withExecutionTimeoutInMilliseconds(conf.getTimeOutMilliSec())));
		this.conf = conf;
		this.requestSettings = requestSettings;
		logger.debug(conf.toString());
	}
	
	
	@Override
	protected String run() throws Exception {
		
		switch (reqType) {
		case GET:
			return new Rest4sCall().getRequest(url);
		case POST:
			return new Rest4sCall().postRequest(url);
		default:
			return new Rest4sCall().sendRequest(requestSettings);
		}
	}
	
	
	@Override
	protected String getFallback() {
		if(this.conf.getFallbackMethodName() == null || "".equals(this.conf.getFallbackMethodName())){
			logger.info("no fallback specified");
			return "no fallback specified";
		}else{
			logger.info("fallback method name : " + this.conf.getFallbackMethodName());
			try {
				Method method = conf.getCallerClass().getMethod(conf.getFallbackMethodName(), new Class<?>[]{});
				method.invoke(conf.getCallerClass().newInstance(), new Object[]{});
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			}
			
			return "fall back called";
		}
		
	}
	
	private static String getDigestedHystrixGroupKey(String groupKey){
		StringBuffer sb = null;
		String result = null;
		try {
			MessageDigest md;
			
			md = MessageDigest.getInstance("MD5");
			md.update(groupKey.getBytes()); 
			
			byte byteData[] = md.digest();
			sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			
		} catch (NoSuchAlgorithmException e) {
			
			result = groupKey;
		}
		
		result = sb.toString();
		
		return result;
	}

}
