package rest4s.config;

public class Rest4sHystrixConfiguration {

	private String fallbackMethodName = null;
	private int timeOutMilliSec = 5000;
	private int sleepWindowMilliSec = 5000;
	private int threasholdVolume = 10;
	private int threasholdErrorPercentage = 50;
	private String healthCheckUrl = null;
	
	private Class<?> callerClass = null;
	
	
	public String getFallbackMethodName() {
		return fallbackMethodName;
	}
	public void setFallbackMethodName(String fallbackMethodName) {
		this.fallbackMethodName = fallbackMethodName;
	}
	public int getTimeOutMilliSec() {
		return timeOutMilliSec;
	}
	public void setTimeOutMilliSec(int timeOutMilliSec) {
		this.timeOutMilliSec = timeOutMilliSec;
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
				+ ", timeOutMilliSec=" + timeOutMilliSec + ", callerClass=" + callerClass
				+ ", sleepWindowMilliSec=" + sleepWindowMilliSec + ", threasholdVolume=" + threasholdVolume
				+ ", threasholdErrorPercentage=" + threasholdErrorPercentage + ", healthCheckUrl=" + healthCheckUrl
				+ "]";
	}
	
	
	
}
