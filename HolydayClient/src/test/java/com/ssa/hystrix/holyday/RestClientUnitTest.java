package com.ssa.hystrix.holyday;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.ssa.hystrix.hello.CommandHelloWorld;
import com.ssa.hystrix.hello.CommandSimple;


public class RestClientUnitTest{

	private String url  = "http://localhost:8080/api/timeline/";
	private CommandHelloWorld command ;
    
    @Test
    public void testSynchronous_basic(){
    	assertEquals("Hello World!", new CommandHelloWorld("World").execute());

        
    }
    
    @Test
    public void testAsynchronous1() throws Exception {
        assertEquals("Hello World!", new CommandHelloWorld("World").queue().get());
    }
    
    @Test
    public void testSynchronousWithDelaySetting() {
    	//assertEquals("Hello World!", new CommandHelloWorld("World").execute());
    	
    	CommandHelloWorld command = new CommandHelloWorld("World", 5000);
    	assertEquals("Hello World!", command.execute());
        
    }

    
    @Test
    public void testSynchronousWithDelaySetting2() {

    
     	CommandHelloWorld command = new CommandHelloWorld("World", 5000);
    	assertEquals("Hello World!", command.execute());
        
    }
    
    @Test
    public void testSettings() throws Exception{


    	CommandHelloWorld command = new CommandHelloWorld("World1",1);
    	
          assertEquals("Hello World10!", command.observe().toBlocking().single());

    }
    
    @Test
    public void testSynchronousUptoVolumeThreshold() throws Exception{

    
    	int i =0;
    	  Observable<String> fWorld1 = new CommandHelloWorld("World1", 2*1000, 4).observe();
    	  Observable<String> fWorld2 = new CommandHelloWorld("World2", 2*1000, 1).observe();    	  
    	  Observable<String> fWorld3 = new CommandHelloWorld("World3", 2*1000, 1).observe();
    	  Observable<String> fWorld4 = new CommandHelloWorld("World4", 2*1000, 4).observe();
    	  Observable<String> fWorld5 = new CommandHelloWorld("World5", 2*1000, 3).observe();
    	  Observable<String> fWorld6 = new CommandHelloWorld("World6", 2*1000, 3).observe();
    	  Observable<String> fWorld7 = new CommandHelloWorld("World7", 2*1000, 3).observe();
    	  Observable<String> fWorld8 = new CommandHelloWorld("World8", 2*1000, 1).observe();
    	  Observable<String> fWorld9 = new CommandHelloWorld("World9", 2*1000, 1).observe();
    	  Observable<String> fWorld10 = new CommandHelloWorld("World10", 2*1000, 1).observe();

          // blocking
          assertEquals("Hello World1!", fWorld1.toBlocking().single());
          assertEquals("Hello World2!", fWorld2.toBlocking().single());
          assertEquals("Hello World3!", fWorld3.toBlocking().single());
          assertEquals("Hello World4!", fWorld4.toBlocking().single());
          assertEquals("Hello World5!", fWorld5.toBlocking().single());
          assertEquals("Hello World6!", fWorld6.toBlocking().single());
          assertEquals("Hello World7!", fWorld7.toBlocking().single());
          assertEquals("Hello World8!", fWorld8.toBlocking().single());
          assertEquals("Hello World9!", fWorld9.toBlocking().single());
          assertEquals("Hello World10!", fWorld10.toBlocking().single());

    }
    
    @Test
    public void testSynchronousUptoVolumeThresholdBlocking() throws Exception{

    
    	CommandHelloWorld command2 =new CommandHelloWorld("World2", 2*1000, 4);
          // blocking
    	assertFalse(true);
          assertEquals("Hello World1!", new CommandHelloWorld("World1", 2*1000, 1).execute());
          
          System.out.println("---" + command2.execute());
          
          assertEquals("Hello World2!", command2.execute());
          System.out.println("isCircuitOpen?:" + command2.isCircuitBreakerOpen());
          
          assertEquals("Hello World1!", new CommandHelloWorld("World3", 2*1000, 1).execute());
          assertEquals("Hello World4!", new CommandHelloWorld("World4", 2*1000, 1).execute());
//          assertEquals("Hello World5!", new CommandHelloWorld("World5", 2*1000, 4).execute());
//          assertEquals("Hello World6!", new CommandHelloWorld("World6", 2*1000, 1).execute());
//          assertEquals("Hello World7!", new CommandHelloWorld("World7", 2*1000, 1).execute());
//          assertEquals("Hello World8!", new CommandHelloWorld("World8", 2*1000, 1).execute());
//          assertEquals("Hello World9!", new CommandHelloWorld("World9", 2*1000, 1).execute());
//          assertEquals("Hello World10!", new CommandHelloWorld("World10", 2*1000, 1).execute());
          


    }
    @Test
    public void testObservable() throws Exception {

        Observable<String> fWorld = new CommandHelloWorld("World").observe();
        Observable<String> fBob = new CommandHelloWorld("Bob").observe();

        // blocking
        assertEquals("Hello World!", fWorld.toBlocking().single());
        System.out.println("### Hello World! EXECUTED");
        assertEquals("Hello Bob!", fBob.toBlocking().single());
        System.out.println("### Hello Bob! EXECUTED");

        // non-blocking 
        // - this is a verbose anonymous inner-class approach and doesn't do assertions
        fWorld.subscribe(new Observer<String>() {

			public void onCompleted() {
				System.out.println("onCompleted() Method executed");
				// TODO Auto-generated method stub
				
			}

			public void onError(Throwable e) {
				System.out.println("onCompleted() Method executed");
				// TODO Auto-generated method stub
				
			}

			public void onNext(String t) {
				// TODO Auto-generated method stub
				  System.out.println("onNext: " + t);
			}


        });

        // non-blocking
        // - also verbose anonymous inner-class
        // - ignore errors and onCompleted signal
        fBob.subscribe(new Action1<String>() {

			public void call(String t) {
				// TODO Auto-generated method stub
				System.out.println("onNext: " + t);
			}

        });

        // non-blocking
        // - using closures in Java 8 would look like this:
        
        //            fWorld.subscribe((v) -> {
        //                System.out.println("onNext: " + v);
        //            })
        
        // - or while also including error handling
        
        //            fWorld.subscribe((v) -> {
        //                System.out.println("onNext: " + v);
        //            }, (exception) -> {
        //                exception.printStackTrace();
        //            })
        
        // More information about Observable can be found at https://github.com/Netflix/RxJava/wiki/How-To-Use

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
}