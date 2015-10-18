/**
 * Copyright 2012 Netflix, Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.hystrix;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.netflix.hystrix.HystrixCommandMetrics.HealthCounts;

/**
 * Circuit-breaker logic that is hooked into {@link HystrixCommand} execution and will stop allowing executions if failures have gone past the defined threshold.
 * <p>
 * It will then allow single retries after a defined sleepWindow until the execution succeeds at which point it will again close the circuit and allow executions again.
 */
public interface HystrixCircuitBreaker {

    /**
     * Every {@link HystrixCommand} requests asks this if it is allowed to proceed or not.
     * <p>
     * This takes into account the half-open logic which allows some requests through when determining if it should be closed again.
     * 
     * @return boolean whether a request should be permitted
     */
    public boolean allowRequest();

    /**
     * Whether the circuit is currently open (tripped).
     * 
     * @return boolean state of circuit breaker
     */
    public boolean isOpen();

    /**
     * Invoked on successful executions from {@link HystrixCommand} as part of feedback mechanism when in a half-open state.
     */
    /* package */void markSuccess();

    /**
     * @ExcludeFromJavadoc
     * @ThreadSafe
     */
    public static class Factory {
        // String is HystrixCommandKey.name() (we can't use HystrixCommandKey directly as we can't guarantee it implements hashcode/equals correctly)
        private static ConcurrentHashMap<String, HystrixCircuitBreaker> circuitBreakersByCommand = new ConcurrentHashMap<String, HystrixCircuitBreaker>();

        /**
         * Get the {@link HystrixCircuitBreaker} instance for a given {@link HystrixCommandKey}.
         * <p>
         * This is thread-safe and ensures only 1 {@link HystrixCircuitBreaker} per {@link HystrixCommandKey}.
         * 
         * @param key
         *            {@link HystrixCommandKey} of {@link HystrixCommand} instance requesting the {@link HystrixCircuitBreaker}
         * @param group
         *            Pass-thru to {@link HystrixCircuitBreaker}
         * @param properties
         *            Pass-thru to {@link HystrixCircuitBreaker}
         * @param metrics
         *            Pass-thru to {@link HystrixCircuitBreaker}
         * @return {@link HystrixCircuitBreaker} for {@link HystrixCommandKey}
         */
        public static HystrixCircuitBreaker getInstance(HystrixCommandKey key, HystrixCommandGroupKey group, HystrixCommandProperties properties, HystrixCommandMetrics metrics) {
            // this should find it for all but the first time
            HystrixCircuitBreaker previouslyCached = circuitBreakersByCommand.get(key.name());
            if (previouslyCached != null) {
                return previouslyCached;
            }

            // if we get here this is the first time so we need to initialize

            // Create and add to the map ... use putIfAbsent to atomically handle the possible race-condition of
            // 2 threads hitting this point at the same time and let ConcurrentHashMap provide us our thread-safety
            // If 2 threads hit here only one will get added and the other will get a non-null response instead.
            HystrixCircuitBreaker cbForCommand = circuitBreakersByCommand.putIfAbsent(key.name(), new HystrixCircuitBreakerImpl(key, group, properties, metrics));
            if (cbForCommand == null) {
                // this means the putIfAbsent step just created a new one so let's retrieve and return it
                return circuitBreakersByCommand.get(key.name());
            } else {
                // this means a race occurred and while attempting to 'put' another one got there before
                // and we instead retrieved it and will now return it
                return cbForCommand;
            }
        }

        /**
         * Get the {@link HystrixCircuitBreaker} instance for a given {@link HystrixCommandKey} or null if none exists.
         * 
         * @param key
         *            {@link HystrixCommandKey} of {@link HystrixCommand} instance requesting the {@link HystrixCircuitBreaker}
         * @return {@link HystrixCircuitBreaker} for {@link HystrixCommandKey}
         */
        public static HystrixCircuitBreaker getInstance(HystrixCommandKey key) {
            return circuitBreakersByCommand.get(key.name());
        }

        /**
         * Clears all circuit breakers. If new requests come in instances will be recreated.
         */
        /* package */static void reset() {
            circuitBreakersByCommand.clear();
        }
    }

    /**
     * The default production implementation of {@link HystrixCircuitBreaker}.
     * 
     * @ExcludeFromJavadoc
     * @ThreadSafe
     */
    /* package */static class HystrixCircuitBreakerImpl implements HystrixCircuitBreaker {
        private final HystrixCommandProperties properties;
        private final HystrixCommandMetrics metrics;

        /* track whether this circuit is open/closed at any given point in time (default to false==closed) */
        private AtomicBoolean circuitOpen = new AtomicBoolean(false);

        /* when the circuit was marked open or was last allowed to try a 'singleTest' */
        private AtomicLong circuitOpenedOrLastTestedTime = new AtomicLong();

        protected HystrixCircuitBreakerImpl(HystrixCommandKey key, HystrixCommandGroupKey commandGroup, HystrixCommandProperties properties, HystrixCommandMetrics metrics) {
            this.properties = properties;
            this.metrics = metrics;
        }

        public void markSuccess() {
            if (circuitOpen.get()) {
                // TODO how can we can do this without resetting the counts so we don't lose metrics of short-circuits etc?
                metrics.resetCounter();
                // If we have been 'open' and have a success then we want to close the circuit. This handles the 'singleTest' logic
                circuitOpen.set(false);
            }
        }

        @Override
        public boolean allowRequest() {
//        	System.out.println("## allowRequest() Called!! currently circuite is.." +  (isOpen()?"OPEN":"CLOSED"));
            if (properties.circuitBreakerForceOpen().get()) {
            	System.out.println("properties.circuitBreakerForceOpen().get() is TRUE // return false");
                // properties have asked us to force the circuit open so we will allow NO requests
                return false;
            }
            if (properties.circuitBreakerForceClosed().get()) {
            	System.out.println("properties.circuitBreakerForceClosed().get() is TRUE //return true");
                // we still want to allow isOpen() to perform it's calculations so we simulate normal behavior
                isOpen();
                // properties have asked us to ignore errors so we will ignore the results of isOpen and just allow all traffic through
                return true;
            }
            System.out.println("!isOpen() || allowSingleTest() returned");
            return !isOpen() || allowSingleTest();
        }

        public boolean allowSingleTest() {
        	System.out.println("## allowSingleTest() Called!!");
            long timeCircuitOpenedOrWasLastTested = circuitOpenedOrLastTestedTime.get();
            // 1) if the circuit is open
            // 2) and it's been longer than 'sleepWindow' since we opened the circuit
            if (circuitOpen.get() && System.currentTimeMillis() > timeCircuitOpenedOrWasLastTested + properties.circuitBreakerSleepWindowInMilliseconds().get()) {
            	System.out.println("## allowSingleTest() Called!! - inside #1");
            	// We push the 'circuitOpenedTime' ahead by 'sleepWindow' since we have allowed one request to try.
                // If it succeeds the circuit will be closed, otherwise another singleTest will be allowed at the end of the 'sleepWindow'.
                if (circuitOpenedOrLastTestedTime.compareAndSet(timeCircuitOpenedOrWasLastTested, System.currentTimeMillis())) {
                    // if this returns true that means we set the time so we'll return true to allow the singleTest
                    // if it returned false it means another thread raced us and allowed the singleTest before we did
                	System.out.println("## allowSingleTest() Called!! - inside #2 returning true");
                	//���⼭ true�� ���ϵǸ� test������ single request �� ���˴ϴ�. 
                	//TODO ���⿡�� ���� health check�� ���� �ϰ�
                	//����� circuit �� ����/�ݾ��ָ� �� ��.
                	boolean targetStatusOk = true;
                	if(this.properties.healthCheckURL != null && !"".equals(this.properties.healthCheckURL)) {
                		targetStatusOk = requestHealthCheck();
                	}
                	
                	//�׸��� false����.
                	//return false;
                    return targetStatusOk;
                }
            }
            System.out.println("## allowSingleTest() Called!! - inside #3 returning false");
            return false;
        }

        @Override
        public boolean isOpen() {
        	System.out.print("### isOpen() called");
            if (circuitOpen.get()) {
                // if we're open we immediately return true and don't bother attempting to 'close' ourself as that is left to allowSingleTest and a subsequent successful test to close
            	System.out.println("### return true ==> OPEN #1");
            	return true;
            }

            // we're closed, so let's see if errors have made us so we should trip the circuit open
            HealthCounts health = metrics.getHealthCounts();

            // check if we are past the statisticalWindowVolumeThreshold
            if (health.getTotalRequests() < properties.circuitBreakerRequestVolumeThreshold().get()) {
                // we are not past the minimum volume threshold for the statisticalWindow so we'll return false immediately and not calculate anything
            	System.out.println("### return false ==> CLOSED #1");
            	return false;
            }

            if (health.getErrorPercentage() < properties.circuitBreakerErrorThresholdPercentage().get()) {
            	System.out.println("### return false ==> CLOSED #2");
            	return false;
            } else {
                // our failure rate is too high, trip the circuit
                if (circuitOpen.compareAndSet(false, true)) {
                    // if the previousValue was false then we want to set the currentTime
                    circuitOpenedOrLastTestedTime.set(System.currentTimeMillis());
                    System.out.println("### return true ==> OPEN #2");
                    return true;
                } else {
                    // How could previousValue be true? If another thread was going through this code at the same time a race-condition could have
                    // caused another thread to set it to true already even though we were in the process of doing the same
                    // In this case, we know the circuit is open, so let the other thread set the currentTime and report back that the circuit is open
                	System.out.println("### return true ==> OPEN #3");
                	return true;
                }
            }
        }
        
        // TODO : ȣ���ϴ� �κ��� hystrix ���� ȣ�� ����� ����ؾ� ���� ������?
        // TODO : Exception Handling ó�� ���� ��Ȯ��
        private boolean requestHealthCheck(){
        	boolean targetStatusOk = true;     		
    		HttpGet httpGet = new HttpGet(this.properties.healthCheckURL);
    		System.out.println("Executing request " + httpGet.getRequestLine());
    		
    		CloseableHttpClient httpClient = HttpClients.createDefault();
    		CloseableHttpResponse response = null;
    		try {
    			response = httpClient.execute(httpGet);
    			int statusCode = response.getStatusLine().getStatusCode();
    			if (statusCode >= 200 && statusCode < 300) {
    				targetStatusOk = true;
    			} else {
    				targetStatusOk = false;
    			}
    		} catch (Exception e) {    			
    			
    			e.printStackTrace();
    		} finally {
    			if(response != null) {
	    			try {
	    				response.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
    			}
    			
    			if(httpClient != null) {
	    			try {
	    				httpClient.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
    			}    			
    		}
        	
        	return targetStatusOk;
        	
        }

    }

    /**
     * An implementation of the circuit breaker that does nothing.
     * 
     * @ExcludeFromJavadoc
     */
    /* package */static class NoOpCircuitBreaker implements HystrixCircuitBreaker {

        @Override
        public boolean allowRequest() {
            return true;
        }

        @Override
        public boolean isOpen() {
            return false;
        }

        @Override
        public void markSuccess() {

        }

    }

}