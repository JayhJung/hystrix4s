package com.ssa.hystrix.hello;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public class CommandHelloWorld extends HystrixCommand<String>{

	private final String name;

    public CommandHelloWorld(String name) {
        super(HystrixCommandGroupKey.Factory.asKey("ExampleGroup"));
        this.name = name;
    }
    
    @Override
    protected String run() {
    	
    	//throw new RuntimeException("blocked");
        // a real example would do work like a network call here
    	System.out.println("Suppose it takes several seconds");
    	try {
    		System.out.println("Running for job: " + name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return "Hello " + name + "!";
    }
    
    
    @Override
    protected String getFallback() {
    	return "Good Bye";
    }
}
