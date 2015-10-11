package rest4s.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Rest4sConf {
	public String fallback() default "getFallback";
	
	public int timeoutSec() default 5;
	
	public int sleepWindowMilliSec() default 5000;
	
	public int threasholdVolume() default 10;
	
	public int threasholdErrorPercentage() default 50;
	
	public String healthCheckUrl() default "/health";
}
