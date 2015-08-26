package com.ssa.hystrix.holyday;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.ssa.hystrix.hello.CommandHelloWorld;
import com.ssa.hystrix.hello.CommandSimple;


public class HolydayClientUnitTest{

	private String url  = "http://localhost:8080/api/timeline/";
	private HolydayRest_1 rest;
	
	@Before
	public void setup(){
		rest = HolydayRest_1.getInstance();
	}
    
    @Test
    public void test_resilient_get() throws Exception {
    	rest.getRs(url);
        
    }

}