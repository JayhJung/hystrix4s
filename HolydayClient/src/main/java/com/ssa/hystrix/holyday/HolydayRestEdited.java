package com.ssa.hystrix.holyday;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.request.GetRequest;
import com.ssa.hystrix.hello.CommandHolyday;
import com.ssa.hystrix.hello.CommandHolydayEdited;

public class HolydayRestEdited{
	
	public static HolydayRestClient get(final String url) {
		
		HolydayRestClient holydayRestClient = new HolydayRestClient(url);
		
		return holydayRestClient;
	}
	
}
