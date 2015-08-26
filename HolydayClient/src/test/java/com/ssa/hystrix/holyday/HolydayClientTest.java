package com.ssa.hystrix.holyday;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.ssa.hystrix.hello.CommandHelloWorld;
import com.ssa.hystrix.hello.CommandSimple;


public class HolydayClientTest{

	String url = "http://localhost:8080/api/timeline/1";
	int i = 0;
    @Test
    public void testClient1() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", 1)
				  .asJson();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testSynchronous2() throws Exception {
    	CommandHelloWorld command = new CommandHelloWorld("World2",2*1000, 4);
    	System.out.println("Circuit status: "+ (++i) + command.isCircuitBreakerOpen());
        assertEquals("Hello World2!",command.execute());
    }
    
    @Test
    public void testSynchronous3() throws Exception {
    	CommandHelloWorld command = new CommandHelloWorld("World3",2*1000, 1);
    	System.out.println("Circuit status: "+ (++i) + command.isCircuitBreakerOpen());
        assertEquals("Hello World3!",command.execute());
    }
    
    @Test
    public void testSynchronous4() throws Exception {
    	CommandHelloWorld command = new CommandHelloWorld("World4",2*1000, 1);
    	System.out.println("Circuit status: "+ (++i) + command.isCircuitBreakerOpen());
        assertEquals("Hello World4!",command.execute());
    }
    
    @Test
    public void testSynchronous5() throws Exception {
    	CommandHelloWorld command = new CommandHelloWorld("World5",2*1000, 1);
    	System.out.println("Circuit status: "+ (++i) + command.isCircuitBreakerOpen());
        assertEquals("Hello World5!",command.execute());
    }
    
    @Test
    public void testSynchronous6() throws Exception {
    	CommandHelloWorld command = new CommandHelloWorld("World6",2*1000, 1);
    	System.out.println("Circuit status: "+ (++i) + command.isCircuitBreakerOpen());
        assertEquals("Hello World6!",command.execute());
    }
    
    @Test
    public void testSynchronous7() throws Exception {
    	Thread.sleep(6000);
    	CommandHelloWorld command = new CommandHelloWorld("World7",2*1000, 1);
    	System.out.println("Circuit status: "+ (++i) + command.isCircuitBreakerOpen());
        assertEquals("Hello World7!",command.execute());
    }
    
    @Test
    public void testSynchronous8() throws Exception {
    	CommandHelloWorld command = new CommandHelloWorld("World8",2*1000, 1);
    	System.out.println("Circuit status: "+ (++i) + command.isCircuitBreakerOpen());
        assertEquals("Hello World8!",command.execute());
    }
    
}