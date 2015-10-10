package hystrix;

import java.lang.reflect.Method;

public class Rest4sConfiguration {

	private String fallbackMethodName = null;
	private int timeOutSec = 5;
	private int sleepWindowMilliSec = 5000;
	private int threasholdVolume = 10;
	private int threasholdErrorPercentage = 50;
	private String healthCheckUrl = "";
	
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
	public int getSleepWindowMilliSec() {
		return sleepWindowMilliSec;
	}
	public void setSleepWindowMilliSec(int sleepWindowMilliSec) {
		this.sleepWindowMilliSec = sleepWindowMilliSec;
	}
	public int getThreasholdVolume() {
		return threasholdVolume;
	}
	public void setThreasholdVolume(int threasholdVolume) {
		this.threasholdVolume = threasholdVolume;
	}
	public int getThreasholdErrorPercentage() {
		return threasholdErrorPercentage;
	}
	public void setThreasholdErrorPercentage(int threasholdErrorPercentage) {
		this.threasholdErrorPercentage = threasholdErrorPercentage;
	}
	public String getHealthCheckUrl() {
		return healthCheckUrl;
	}
	public void setHealthCheckUrl(String healthCheckUrl) {
		this.healthCheckUrl = healthCheckUrl;
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
