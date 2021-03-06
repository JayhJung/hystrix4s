package com.ssa.hystrix.holyday;

import com.mashape.unirest.http.HttpMethod;
import com.mashape.unirest.request.GetRequest;
import com.ssa.hystrix.hello.CommandHolyday;

public class HolydayRest{

	public static GetRequest get(final String url) {
		
		CommandHolyday command = new CommandHolyday(url) {
		
			@Override
			public void holydayRun() throws Exception{
				System.out.println("## holydayRun(); INSIDE before");
				(new GetRequest(HttpMethod.GET, url))
						  .header("accept", "application/json")
						  .queryString("delay", 1)
						  .asJson();
				System.out.println("## holydayRun(); INSIDE Afeter");
			}
			
			@Override
			public void holydayFallback() {
				//DO NOTHING
			}
		};
		System.out.println("command.execute() BEFORE");
		System.out.println(command.execute());
		System.out.println("command.execute() AFTER");
		return command.response;
	}
	
	//TODO Extension Point : fallback을 계속해서 지정할 수 있도록 변경.
	public static GetRequest get(final String url, String fallbackUrl) {
		
		CommandHolyday command = new CommandHolyday(url) {
		
			@Override
			public void holydayRun() {
				response = new GetRequest(HttpMethod.GET, url);
			}
			
			@Override
			public void holydayFallback() {
				response = new GetRequest(HttpMethod.GET, url);
			}
		};
		command.execute();
		return command.response;
	}
	
}
