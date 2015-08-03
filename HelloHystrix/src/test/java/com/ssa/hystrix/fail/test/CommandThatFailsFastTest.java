package com.ssa.hystrix.fail.test;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.ssa.hystrix.fail.CommandThatFailsFast;

public class CommandThatFailsFastTest {

	        @Test
	        public void testSuccess() {
	            assertEquals("success", new CommandThatFailsFast(false).execute());
	        }

	        @Test
	        public void testFailure() {
	            try {
	                new CommandThatFailsFast(true).execute();
	                fail("we should have thrown an exception");
	            } catch (HystrixRuntimeException e) {
	                assertEquals("failure from CommandThatFailsFast", e.getCause().getMessage());
	                e.printStackTrace();
	            }
	        }

}
