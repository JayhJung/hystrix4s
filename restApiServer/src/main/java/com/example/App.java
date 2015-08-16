package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */

@EnableAutoConfiguration
@ComponentScan
public class App 
{
	
	/*@RequestMapping("/")
	public String home(){
		return "Hello World!!!!";
		
	}*/
	
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
}
