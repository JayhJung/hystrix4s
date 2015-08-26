package com.ssa.hystrix.hello;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;

public abstract class CommandHolyday extends HystrixCommand<String>{
	
	private final String url;
	private static String getHystrixGroupKey(String url){
		try {
		MessageDigest md;
			md = MessageDigest.getInstance("MD5");
		md.update(url.getBytes()); 
		byte byteData[] = md.digest();
		StringBuffer sb = new StringBuffer(); 
		for(int i = 0 ; i < byteData.length ; i++){
			sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
		} catch (NoSuchAlgorithmException e) {
			return url;
		}
	}
	
	public CommandHolyday(String url) {
		super(HystrixCommandGroupKey.Factory.asKey(getHystrixGroupKey(url)));
        this.url = url;
	}

    @Override
    protected String getFallback() {
    	System.out.println("##" + url + " ## Fall back called!!");
    	holydayFallback();
    	return super.getFallback();
    }
    public abstract void holydayFallback();


	@Override
	protected String run() throws Exception {
		holydayRun();
		return url + " called!!";
	}
	
	public abstract void holydayRun();
	
	
}
