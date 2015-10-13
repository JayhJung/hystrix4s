package clients;

import hystrix.CommandRest4s;
import hystrix.Rest4sConfiguration;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.springframework.beans.factory.config.RuntimeBeanNameReference;

import annotation.Rest4sConf;
import type.RequestType;

public class Rest4sCall {

	public static String GetRequest(String url) throws Exception {
		Rest4sConfiguration conf = Rest4sCall.parseAnnotations();
		CommandRest4s command = new CommandRest4s(url, RequestType.GET, conf);
		String result = command.execute();
		return result;
	}

	public static String PostRequest(String url) throws Exception {
//		Rest4sCall req = new Rest4sCall();
		Rest4sConfiguration conf = Rest4sCall.parseAnnotations();
		CommandRest4s command = new CommandRest4s(url, RequestType.POST, conf);
		String result = command.execute();
		return result;
	}

	private static Rest4sConfiguration parseAnnotations() {
		StackTraceElement[] stacks = new Throwable().getStackTrace();
		Rest4sConfiguration conf = new Rest4sConfiguration();
		if (stacks == null || stacks.length <= 1) {
			throw new RuntimeException();
		}

		try {
			conf.setCallerClass(Class.forName(stacks[2].getClassName()));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
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
						conf.setTimeOutSec(myAnnotation.timeoutSec());
						conf.setSleepWindowMilliSec(myAnnotation.sleepWindowMilliSec());
						conf.setThreasholdVolume(myAnnotation.threasholdVolume());
						conf.setThreasholdErrorPercentage(myAnnotation.threasholdErrorPercentage());
						conf.setHealthCheckUrl(myAnnotation.healthCheckUrl());
						System.out.println("### " + myAnnotation.timeoutSec());
					}
				}
			}
		}

		return conf;
	}

}
