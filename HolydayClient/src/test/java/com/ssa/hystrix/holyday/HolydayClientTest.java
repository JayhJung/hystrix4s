package com.ssa.hystrix.holyday;



import org.junit.Test;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.request.GetRequest;


public class HolydayClientTest{

	String url = "http://localhost:8080/api/timeline/1";
	int i = 0;
    @Test
    public void testClient1() throws Exception {
    	
    	GetRequest reponse = HolydayRest.get(url);
				 
    	//System.out.println(reponse.getBody());
    }
    
    
    @Test
    public void testClient2() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", 2)
				  .asJson();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient3() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", 1)
				  .asJson();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient4() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", 1)
				  .asJson();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient5() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", 1)
				  .asJson();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient6() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", 1)
				  .asJson();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient7() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", 1)
				  .asJson();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient8() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", 1)
				  .asJson();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient9() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", 1)
				  .asJson();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient10() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRest.get(url)
				  .header("accept", "application/json")
				  .queryString("delay", 1)
				  .asJson();
    	System.out.println(reponse.getBody());
    }
  
  
    
}