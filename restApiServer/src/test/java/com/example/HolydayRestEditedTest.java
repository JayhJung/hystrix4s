package com.example;



import org.junit.Test;

import com.example.hystrix.HolydayRestClient;
import com.example.hystrix.HolydayRestEdited;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;


public class HolydayRestEditedTest{

	String url = "http://localhost:8080/restserver/rest/1";
	int i = 0;
    @Test
    public void testClient1() throws Exception {
    	
    	HolydayRestClient client = HolydayRestEdited.get(url);
    	client = client.header("accept", "application/json");
    	
    	HttpResponse<JsonNode> reponse = HolydayRestEdited.get(url)
    			.header("accept", "application/json")
    			.queryString("delay", 3)
    			.sendRequest();
				 
    	System.out.println(reponse.getBody());
    }
    
    
    @Test
    public void testClient2() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRestEdited.get(url)
    			.header("accept", "application/json")
    			.queryString("delay", 1)
    			.sendRequest();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient3() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRestEdited.get(url)
    			.header("accept", "application/json")
    			.queryString("delay", 3)
    			.sendRequest();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient4() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRestEdited.get(url)
    			.header("accept", "application/json")
    			.queryString("delay", 1)
    			.sendRequest();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient5() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRestEdited.get(url)
    			.header("accept", "application/json")
    			.queryString("delay", 1)
    			.sendRequest();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient6() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRestEdited.get(url)
    			.header("accept", "application/json")
    			.queryString("delay", 1)
    			.sendRequest();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient7() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRestEdited.get(url)
    			.header("accept", "application/json")
    			.queryString("delay", 1)
    			.sendRequest();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient8() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRestEdited.get(url)
    			.header("accept", "application/json")
    			.queryString("delay", 1)
    			.sendRequest();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient9() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRestEdited.get(url)
    			.header("accept", "application/json")
    			.queryString("delay", 1)
    			.sendRequest();
    	System.out.println(reponse.getBody());
    }
    
    @Test
    public void testClient10() throws Exception {
    	
    	HttpResponse<JsonNode> reponse = HolydayRestEdited.get(url)
    			.header("accept", "application/json")
    			.queryString("delay", 1)
    			.sendRequest();
    	System.out.println(reponse.getBody());
    }
  
  
    
}