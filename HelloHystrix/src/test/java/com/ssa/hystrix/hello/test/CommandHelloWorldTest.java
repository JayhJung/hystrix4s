package com.ssa.hystrix.hello.test;


import static org.junit.Assert.assertEquals;

import java.util.concurrent.Future;

import org.junit.Test;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

import com.ssa.hystrix.hello.CommandHelloWorld;


public class CommandHelloWorldTest{

    @Test
    public void testSynchronous() {
        assertEquals("Hello World!", new CommandHelloWorld("World").execute());
        assertEquals("Hello Bob!", new CommandHelloWorld("Bob").execute());
    }

    @Test
    public void testAsynchronous1() throws Exception {
        assertEquals("Hello World!", new CommandHelloWorld("World").queue().get());
        assertEquals("Hello Bob!", new CommandHelloWorld("Bob").queue().get());
    }

    @Test
    public void testAsynchronous2() throws Exception {

        Future<String> fWorld = new CommandHelloWorld("World").queue();
        Future<String> fBob = new CommandHelloWorld("Bob").queue();

        assertEquals("Hello World!", fWorld.get());
        assertEquals("Hello Bob!", fBob.get());
    }

    @Test
    public void testResponseFailure() throws Exception{
    	   String failMessage = new CommandHelloWorld("World").execute();
    	   assertEquals(failMessage, "Good Bye");
    	      
    }
    
    @Test
    public void testObservable() throws Exception {

        Observable<String> fWorld = new CommandHelloWorld("World").observe();
        Observable<String> fBob = new CommandHelloWorld("Bob").observe();

        // blocking
        assertEquals("Hello World!", fWorld.toBlocking().single());
        assertEquals("Hello Bob!", fBob.toBlocking().single());

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
    
}