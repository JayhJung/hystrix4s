package com.example.hystrix;


public class HolydayRestEdited{
	
	public static HolydayRestClient get(final String url) {
		
		HolydayRestClient holydayRestClient = new HolydayRestClient(url);
		
		return holydayRestClient;
	}
	
}
