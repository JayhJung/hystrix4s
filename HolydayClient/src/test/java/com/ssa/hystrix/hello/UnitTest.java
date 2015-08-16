package com.ssa.hystrix.hello;


import static org.junit.Assert.assertEquals;

import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;


public class UnitTest{

	private String url  = "http://localhost:8080/api/timeline/";
	private CommandHelloWorld command ;
	
	@Before
	public void preSettings(){
		 command = new CommandHelloWorld("World", 5);
	}
	
    @Test
    public void testCommandSimpleSynchronous() {
        assertEquals("Hello World!", new CommandSimple("World").execute());
        
        assertEquals("Hello World!", new CommandSimple("World").execute());
    }

    
    @Test
    public void testCaching(){
    	HystrixRequestContext context = HystrixRequestContext.initializeContext();
    	
    	 try {
    		 assertEquals("Hello World!", new CommandSimple("World").execute());
    		 System.out.println("10"); 
    		 assertEquals("Hello World!", new CommandSimple("World").execute());
    		 System.out.println("20"); 
    		 assertEquals("Hello World!", new CommandSimple("World").execute());
    		 System.out.println("30"); 
    		 assertEquals("Hello World!", new CommandSimple("World1").execute());
    		 System.out.println("40"); 
    		 assertEquals("Hello World!", new CommandSimple("World").execute());
    		 System.out.println("50"); 
         } finally {
             context.shutdown();
         }
    	 
    }
    
    @Test
    public void testSynchronousWithDelaySetting() {
    	assertEquals("Hello World!", new CommandHelloWorld("World").execute());
/*    	
    	CommandHelloWorld command = new CommandHelloWorld("World", 5);
        command.setCallUrl(url);
    	command.setDelay(1);
    	assertEquals("Hello World!", command.execute());*/
        
    }
    @Test
    public void testAsynchronous1() throws Exception {

    	Future<String> fBob3 = new CommandHelloWorld_3("Bob").queue();
    	System.out.println("fBob3 Queued...");
    	Future<String> fBob1 = new CommandHelloWorld_1("Bob").queue();
    	System.out.println("fBob1 Queued...");
    	Future<String> fBob5 = new CommandHelloWorld_5("Bob").queue();
    	System.out.println("fBob5 Queued...");
    	assertEquals("Hello Bob!", fBob3.get());
    	System.out.println("10");
    	assertEquals("Hello Bob!", fBob5.get());
    	System.out.println("20");
    	assertEquals("Hello Bob!", fBob1.get());
        System.out.println("30");
        assertEquals("Hello Bob!", fBob1.get());
        System.out.println("40");
    }

    

    @Test
    public void testObservable() throws Exception {

        Observable<String> fWorld = new CommandHelloWorld_3("World").observe();
        Observable<String> fBob = new CommandHelloWorld_1("Bob").toObservable();
        
        System.out.println("1");
        // blocking
       assertEquals("Hello World!", fWorld.toBlocking().single());
       System.out.println("2");
       //assertEquals("Hello Bob!", fBob.toBlocking().single());
        
        // non-blocking 
        // - this is a verbose anonymous inner-class approach and doesn't do assertions
       fWorld.subscribe(new Observer<String>() {

			public void onCompleted() {
				// TODO Auto-generated method stub
				System.out.println("fWorld onComplete");
				
			}

			public void onError(Throwable e) {
				// TODO Auto-generated method stub
				System.out.println("fWorld Error");
				
			}

			public void onNext(String t) {
				// TODO Auto-generated method stub
				  System.out.println("onNext_fWorld: " + t);
			}
        });

        // non-blocking
        // - also verbose anonymous inner-class
        // - ignore errors and onCompleted signal
        fBob.subscribe(new Action1<String>() {

			public void call(String t) {
				// TODO Auto-generated method stub
				System.out.println("onNext_fBob: " + t);
			}



        });


    }
}