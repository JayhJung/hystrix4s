package rest4s.restclient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import rest4s.config.RequestType;
import rest4s.config.Rest4sHystrixConfiguration;
import rest4s.config.annotation.Rest4sConf;

public class Rest4s {
	
	private static final Logger logger = LoggerFactory.getLogger(Rest4s.class);

	/**
	 * 
	 * simple get method call request only with target url
	 * without any header, cookies, etc, and return response data 
	 * converted as string.
	 * 
	 */
	public static String simpleGet(String url) throws Exception {
		Rest4sHystrixConfiguration conf = Rest4s.parseAnnotations(url);
		Rest4sHystrixCommand command = new Rest4sHystrixCommand(url, RequestType.GET, conf);
		String result = command.execute();
		
		return result;
	}

	/**
	 * 
	 * simple post method call request only with target url and empty body
	 * without any header, cookies, etc, and return response data 
	 * converted as string.
	 * 
	 */
	public static String simplePost(String url) throws Exception {
		Rest4sHystrixConfiguration conf = Rest4s.parseAnnotations(url);
		Rest4sHystrixCommand command = new Rest4sHystrixCommand(url, RequestType.POST, conf);
		String result = command.execute();
		
		return result;
	}

	/**
	 * 
	 * call request with requestSettings including headers, cookies, queryString
	 * 
	 */
	public static String callRequest(RequestSettings requestSettings) {

		Rest4sHystrixConfiguration conf = Rest4s.parseAnnotations(requestSettings.getUrl());
		Rest4sHystrixCommand command = new Rest4sHystrixCommand(requestSettings, conf);
		String result = command.execute();
		
		return result;
	}

	private static Rest4sHystrixConfiguration parseAnnotations(String url) {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		Rest4sHystrixConfiguration conf = new Rest4sHystrixConfiguration();
		if (stacks == null || stacks.length <= 1) {
			throw new RuntimeException();
		}

		try {
			conf.setCallerClass(Class.forName(stacks[2].getClassName()));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		Method[] methods = conf.getCallerClass().getMethods();
		Annotation[] methodAnnotations = null;
		for (Method m : methods) {
			if (m.getName().equals(stacks[2].getMethodName())) {
				methodAnnotations = m.getDeclaredAnnotations();
				for (Annotation annoItem : methodAnnotations) {
					if (annoItem instanceof Rest4sConf) {
						Rest4sConf myAnnotation = (Rest4sConf) annoItem;
						conf.setFallbackMethodName(myAnnotation.fallback());
						conf.setTimeOutMilliSec(myAnnotation.timeoutMilliSec());
						conf.setSleepWindowMilliSec(myAnnotation.sleepWindowMilliSec());
						conf.setThreasholdVolume(myAnnotation.threasholdVolume());
						conf.setThreasholdErrorPercentage(myAnnotation.threasholdErrorPercentage());
						
						String scheme = url.split("://")[0];
						String path = url.split("://")[1];
						String host = path.split("/")[0];						
						String targetHost = scheme.concat("://").concat(host);						
						conf.setHealthCheckUrl(targetHost.concat(myAnnotation.healthCheckUrl()));
						
						logger.debug("### target url : " + conf.getHealthCheckUrl());
						logger.debug("### " + myAnnotation.timeoutMilliSec());
					}
				}
			}
		}

		return conf;
	}

}
