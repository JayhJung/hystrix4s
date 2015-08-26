package com.ssa.hystrix.holyday;

import java.util.Map;

public class ConnectionConfig {
	
	private int circuitBreakerRequestVolumeThreshold;
	private int circuitBreakerSleepWindowInMilliseconds;
	private int circuitBreakerErrorThresholdPercentage;
	
	private int metricsRollingStatisticalWindowInMilliseconds;
	private int metricsRollingStatisticalWindowBuckets;
	private int metricsRollingPercentileWindowInMilliseconds;
	private int metricsRollingPercentileWindowBuckets;
	
	private Map<String, String> configMap;
	
	
	public ConnectionConfig() {
		
	}

	
	/* Circuit Breaker related configuration items */
	public ConnectionConfig cbVolumeThreshold(int circuitBreakerRequestVolumeThreshold) {
		this.circuitBreakerRequestVolumeThreshold = circuitBreakerRequestVolumeThreshold;
		
		this.configMap.put("circuitBreakerRequestVolumeThreshold", Integer.toString(circuitBreakerRequestVolumeThreshold));
		
		return this;
	}
	
	public ConnectionConfig cbSleepWindowInMilliseconds(int circuitBreakerSleepWindowInMilliseconds) {
		this.circuitBreakerSleepWindowInMilliseconds = circuitBreakerSleepWindowInMilliseconds;
		
		this.configMap.put("circuitBreakerSleepWindowInMilliseconds", Integer.toString(circuitBreakerSleepWindowInMilliseconds));
		
		return this;
	}
	
	public ConnectionConfig cbErrorThresholdPercentage(int circuitBreakerErrorThresholdPercentage) {
		this.circuitBreakerErrorThresholdPercentage = circuitBreakerErrorThresholdPercentage;
		
		this.configMap.put("circuitBreakerErrorThresholdPercentage", Integer.toString(circuitBreakerErrorThresholdPercentage));
		
		return this;
	}
	
	
	/* Circuit Breaker related configuration items */
	public ConnectionConfig mtrRollingStatisticalWindowInMilliseconds(int metricsRollingStatisticalWindowInMilliseconds) {
		this.metricsRollingStatisticalWindowInMilliseconds = metricsRollingStatisticalWindowInMilliseconds;
		
		this.configMap.put("metricsRollingStatisticalWindowInMilliseconds", Integer.toString(metricsRollingStatisticalWindowInMilliseconds));
		
		return this;
	}
	
	public ConnectionConfig mtrRollingStatisticalWindowBuckets(int metricsRollingStatisticalWindowBuckets) {
		this.metricsRollingStatisticalWindowBuckets = metricsRollingStatisticalWindowBuckets;
		
		this.configMap.put("metricsRollingStatisticalWindowBuckets", Integer.toString(metricsRollingStatisticalWindowBuckets));
		
		return this;
	}
	
	public ConnectionConfig mtrRollingPercentileWindowInMilliseconds(int metricsRollingPercentileWindowInMilliseconds) {
		this.metricsRollingPercentileWindowInMilliseconds = metricsRollingPercentileWindowInMilliseconds;
		
		this.configMap.put("metricsRollingPercentileWindowInMilliseconds", Integer.toString(metricsRollingPercentileWindowInMilliseconds));
		
		return this;
	}
	
	public ConnectionConfig mtrRollingPercentileWindowBuckets(int metricsRollingPercentileWindowBuckets) {
		this.metricsRollingPercentileWindowBuckets = metricsRollingPercentileWindowBuckets;
		
		this.configMap.put("metricsRollingPercentileWindowBuckets", Integer.toString(metricsRollingPercentileWindowBuckets));
		
		return this;
	}
	
	public Map<String, String> toMap() {
		return this.configMap;
	}
	
}
