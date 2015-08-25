package com.ssa.hystrix.holyday;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import rx.Observer;
import rx.functions.Action1;

import com.ssa.hystrix.hello.CommandHelloWorld;

public class SimpleUnitTest {
	
	 @Test
    public void testCommandSimpleSynchronous() {
    	System.out.println("very basic hystrix command.");
        assertEquals("Hello World!", new CommandHelloWorld("World").execute());
    }
    
   
    public void testCommandsimpleASync(){
    	
    	//어떻게 동작을 하게 만드는것인가?
    	rx.Observable<String> ho  = new CommandHelloWorld("World").observe();


    	ho.subscribe(new Action1<String>() {

    	    @Override
    	    public void call(String s) {
    	    	System.out.println("// value emitted here: " + s);
    	    }

    	});
    	
    	ho.subscribe(new Observer<String>() {

            @Override
            public void onCompleted() {
                // nothing needed here
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(String v) {
                System.out.println("onNext: " + v);
            }

        });
    	
    }

   
}
