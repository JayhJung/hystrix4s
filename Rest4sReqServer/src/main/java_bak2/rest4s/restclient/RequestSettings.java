package rest4s.restclient;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;

public class RequestSettings {
	
	private String url;
	private String httpRequestMethod;
	private List<Header> httpRequestHeaders;
	private String queryParameterString;
	private CookieStore cookieStore;
		
	/**
	 * Request Settings Class Constructor
	 * 
	 * @param url
	 */
	public RequestSettings(String url) {
		
		if(url != null && !"".equals(url)) {
			
			String urlRegex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			Pattern pattern = Pattern.compile(urlRegex);
            Matcher matcher = pattern.matcher(url);
            if(matcher.matches()) {
            	this.url = url;
            } else {
    			throw new IllegalArgumentException();
            }			
		} else {
			throw new IllegalArgumentException();
		}
		
		this.httpRequestMethod = "get";
	}
	
	/**
	 * Request Settings Class Constructor
	 * 
	 * @param url
	 * @param httpMethod
	 */
	public RequestSettings(String url, String httpMethod) {
		
		if(url != null && !"".equals(url)) {
			
			String urlRegex = "\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
			Pattern pattern = Pattern.compile(urlRegex);
            Matcher matcher = pattern.matcher(url);
            if(matcher.matches()) {
            	this.url = url;
            } else {
    			throw new IllegalArgumentException();
            }			
		} else {
			throw new IllegalArgumentException();
		}
		
		this.httpRequestMethod = httpMethod;
		
	}
	
	/**
	 * get url
	 * 
	 * @return url
	 */
	public String getUrl() {
		return this.url;
	}
	
	/**
	 * set HTTP METHOD
	 * 
	 * @param method
	 * @return void
	 */
	public void setHttpRequestMethod(String method) {
		if(method != null && !"".equals(method)) {
			
			if(method.equalsIgnoreCase("get") || method.equalsIgnoreCase("head") ||
					method.equalsIgnoreCase("post") || method.equalsIgnoreCase("put") ||
					method.equalsIgnoreCase("delete") || method.equalsIgnoreCase("options") ||
					method.equalsIgnoreCase("options")) {
				
				this.httpRequestMethod = method.toLowerCase();
			} else {
				
				throw new IllegalArgumentException();
			}
		} else {
			
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * set HTTP METHOD
	 * 
	 * @param method
	 * @return RequestSettings
	 */
	public RequestSettings method(String method) {
		
		this.setHttpRequestMethod(method);
		
		return this;
	}
	
	/**
	 * get HTTP METHOD
	 * 
	 * @return httpRequestMethod
	 */
	public String getHttpRequestMethod() {
		return this.httpRequestMethod;
	}
	
	/**
	 * set HTTP request header
	 * 
	 * @param key
	 * @param value
	 * @return void
	 */
	public void addHeader(String key, String value) {
		
		if((key != null && !"".equals(key)) && (value != null && !"".equals(value))) {
			
			Header header = new BasicHeader(key, value);
			
			this.httpRequestHeaders.add(header);
			
		} else {
			
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * set HTTP request header
	 * 
	 * @param key
	 * @param value
	 * @return RequestSettings
	 */
	public RequestSettings header(String key, String value) {
		this.addHeader(key, value);
		
		return this;
	}
	
	/**
	 * get HTTP request header
	 * 
	 * @param key
	 * @return value
	 */
	public String getHeader(String key) {
		
		String value = null;
		
		for(Header header : httpRequestHeaders) {
			if(key.equals(header.getName())) {
				value = header.getValue();
				break;
			}
		}
		
		return value;
	}
	
	/**
	 * get HTTP request headers
	 * 
	 * @param key
	 * @return value
	 */
	public List<Header> getHeaders() {
			
		return this.httpRequestHeaders;
	}
	
	/**
	 * set HTTP request headers
	 * 
	 * @param headers
	 * @return void
	 */
	public void addHeaders(Map<String, String> headers) {
		if(!headers.isEmpty()) {
			for(String key : headers.keySet()) {
				Header header = new BasicHeader(key, headers.get(key));
				
				this.httpRequestHeaders.add(header);
			}
			
		} else {
			
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * set HTTP request headers
	 * 
	 * @param headers
	 * @return RequestSettings
	 */
	public RequestSettings headers(Map<String, String> headers) {
		this.addHeaders(headers);
		
		return this;
	}
	
	/**
	 * set HTTP request cookie
	 * 
	 * @param key
	 * @param value
	 * @return void
	 */
	public void addCookie(String key, String value) {
		if(this.cookieStore == null) {
			this.cookieStore = new BasicCookieStore();
		}
		
		if((key != null && !"".equals(key)) && (value != null && !"".equals(value))) {
			
			BasicClientCookie cookie = new BasicClientCookie(key, value);
			this.cookieStore.addCookie(cookie);
			
		} else {
			
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * set HTTP request cookie
	 * 
	 * @param key
	 * @param value
	 * @return RequestSettings
	 */
	public RequestSettings cookie(String key, String value) {
		this.addCookie(key, value);
		
		return this;
	}
	
	/**
	 * set HTTP request cookies
	 * 
	 * @param cookies
	 * @return void
	 */
	public void addCookies(Map<String, String> cookies) {
		if(this.cookieStore == null) {
			cookieStore = new BasicCookieStore();
		}
		
		if(!cookies.isEmpty()) {
			for(String key : cookies.keySet()) {
				
				BasicClientCookie cookie = new BasicClientCookie(key, cookies.get(key));
				this.cookieStore.addCookie(cookie);
			}
			
		} else {
			
			throw new IllegalArgumentException();
		}
	}
	
	/**
	 * set HTTP request cookies
	 * 
	 * @param cookies
	 * @return RequestSettings
	 */
	public RequestSettings cookies(Map<String, String> cookies) {
		this.addCookies(cookies);
		
		return this;
	}
	
	/**
	 * set cookie domain and path
	 * 
	 * @return boolean
	 */
	public boolean setCookieDomainAndPath(String cookieName, String cookieDomain, String cookiePath) {
		boolean result = false;
		
		if(this.cookieStore == null) {
			result = false;
		} else if((cookieName == null || "".equals(cookieName)) && (cookiePath == null || "".equals(cookiePath))) {
			result = false;
		} else {
			for(Cookie cookie : this.cookieStore.getCookies()) {
				if(cookieName.equals(cookie.getName())) {
					if(cookieName != null && !"".equals(cookieName)) {
						((BasicClientCookie)cookie).setDomain(cookieDomain);
						result = true;
					}
					
					if(cookiePath != null && !"".equals(cookiePath)) {
						((BasicClientCookie)cookie).setPath(cookiePath);
						result = true;
					}
					
					break;
				}
			}
		}
		
		return result;
	}
	
	/**
	 * set cookie domain
	 * 
	 * @return boolean
	 */
	public boolean setCookieDomain(String cookieName, String cookieDomain) {
		
		return this.setCookieDomainAndPath(cookieName, cookieDomain, null);
	}
	
	/**
	 * set cookie path
	 * 
	 * @return boolean
	 */
	public boolean setCookiePath(String cookieName, String cookiePath) {
		
		return this.setCookieDomainAndPath(cookieName, null, cookiePath);
	}
	
	/**
	 * set HTTP request cookies
	 * 
	 * @return CookieStore
	 */
	public CookieStore getCookieStore() {
		
		return this.cookieStore;
	}
	
	/**
	 * set HTTP request cookies
	 * 
	 * @return CookieStore
	 */
	public Cookie getCookie(String cookieName) {
		
		Cookie cookie = null;
		
		if(cookieName == null || "".equals(cookieName)) {
			return cookie;
		} else {
			for(Cookie item : this.cookieStore.getCookies()) {
				if(cookieName.equals(item.getName())) {
					cookie = item;
				}
			}
		}
		
		return cookie;
	}
			
	/**
	 * set HTTP request query parameter
	 * 
	 * @param cookies
	 * @return RequestSettings
	 */
	public void setQueryParameterString(String wholeQueryParameterString) {
		// wholeQueryString include '?' character and everything
		// need URLEncoding ??
		this.queryParameterString = wholeQueryParameterString;
		
	}
	
	public String getQueryParameterString() {
		
		return this.queryParameterString;
	}
	
	// TODO : querystring builder method
	
}
