package rest4s.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Rest4sDefault {
	String fallback() default "getFallback";
	
	int timeoutMilliSec() default 5000;
	
	int sleepWindowMilliSec() default 5000;
	
	int threasholdVolume() default 10;
	
	int threasholdErrorPercentage() default 50;
	
	String healthCheckUrl() default "/health";
	
}
