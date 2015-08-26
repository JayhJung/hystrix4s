package com.ssa.hystrix.hello;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import com.netflix.hystrix.HystrixCommand;

public class UniRestWrapping extends HystrixCommand<T>{
	public static void main(String[] args) {
		UniRestWrapping.holydayGet("aaa");
	}
	static String url;
	GetRequest request;
	
	public static R holydayGet(String p_url){
		url = p_url;
		UniRestWrapping command = new UniRestWrapping();
		 command.execute();
		return
	}



	@Override
	protected T run() throws Exception {
		request = Unirest.get(url);
		
		
		return ;
	}
	
	@Override
	protected String getFallback() {
		// TODO Auto-generated method stub
		return super.getFallback();
	}
}
