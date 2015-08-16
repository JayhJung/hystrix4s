package com.ssa.hystrix.hello;

import org.apache.http.client.HttpClient;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class NormalClient {

	public static void main(String[] args) throws Exception {
		
		
		String addr = "http://localhost:8080/api/timeline/";
		String addr2 = "http://httpbin.org/post";
		HttpResponse<JsonNode> jsonResponse = Unirest.get(addr)
				  .header("accept", "application/json")
				  .queryString("apiKey", "123")
				  .asJson();
		//http://192.168.0.19:8080/api/timeline/
		System.out.println(jsonResponse.getBody());
		
		
		/*Unirest.get(url)
		Unirest.delete(url)
		Unirest.setHttpClient(httpClient);
		HttpClient */
		
	}
}
