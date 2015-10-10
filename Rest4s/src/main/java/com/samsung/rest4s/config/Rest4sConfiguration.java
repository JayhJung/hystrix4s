package com.samsung.rest4s.config;

import java.lang.reflect.Method;

public class Rest4sConfiguration {

	private String fallbackMethodName = null;
	private int timeOutSec = 5;
	private Class<?> callerClass = null;
	
	
	public String getFallbackMethodName() {
		return fallbackMethodName;
	}
	public void setFallbackMethodName(String fallbackMethodName) {
		this.fallbackMethodName = fallbackMethodName;
	}
	public int getTimeOutSec() {
		return timeOutSec;
	}
	public void setTimeOutSec(int timeOutSec) {
		this.timeOutSec = timeOutSec;
	}
	public Class<?> getCallerClass() {
		return callerClass;
	}
	public void setCallerClass(Class<?> callerClass) {
		this.callerClass = callerClass;
	}
	@Override
	public String toString() {
		return "Rest4sConfiguration [fallbackMethodName=" + fallbackMethodName
				+ ", timeOutSec=" + timeOutSec + ", callerClass=" + callerClass
				+ "]";
	}
	
	
	
}
