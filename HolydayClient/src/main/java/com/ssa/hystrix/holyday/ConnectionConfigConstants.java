package com.ssa.hystrix.holyday;

public class ConnectionConfigConstants {
	
	/* Circuit Breaker configuration related constants */
	public static String CB_REQUEST_VOLUME_THRESHOLD = "circuitBreakerRequestVolumeThreshold";
	
	public static String CB_SLEEP_WINDOW_IN_MILLI_SECONDS = "circuitBreakerSleepWindowInMilliseconds";
	
	public static String CB_ERROR_THRESHOLD_PERCENTAGE = "circuitBreakerErrorThresholdPercentage";
	
	
	/* Metrics configuration related constants */
	public static String MTR_ROLLING_STAISTICAL_WINDOW_IN_MILLISECONDS = "metricsRollingStatisticalWindowInMilliseconds";
	
	public static String MTR_ROLLING_STAISTICAL_WINDOW_BUCKETS = "metricsRollingStatisticalWindowBuckets";
	
	public static String MTR_ROLLING_PERCENTILE_WINDOW_IN_MILLISECONDS = "metricsRollingPercentileWindowInMilliseconds";
	
	public static String MTR_ROLLING_PERCENTILE_WINDOW_BUCKETS = "metricsRollingPercentileWindowBuckets";
	
}
