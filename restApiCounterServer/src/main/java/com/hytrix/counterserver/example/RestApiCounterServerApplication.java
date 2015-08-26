package com.hytrix.counterserver.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan
public class RestApiCounterServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApiCounterServerApplication.class, args);        
    }
}
