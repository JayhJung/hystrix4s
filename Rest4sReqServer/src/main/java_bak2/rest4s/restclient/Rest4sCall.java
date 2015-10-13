package rest4s.restclient;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rest4sCall {
	
	private static final Logger logger = LoggerFactory.getLogger(Rest4sCall.class);
	
	private CloseableHttpClient httpclient = HttpClients.createDefault();
	
	private ResponseHandler<String> simpleResponseHandler = new ResponseHandler<String>() {
		@Override
		public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				return entity != null ? EntityUtils.toString(entity)
						: null;
			} else {
				throw new ClientProtocolException(
						"Unexpected response status: " + status);
			}
		}

	};
	
	private HttpUriRequest createHttpRequest(RequestSettings requestSettings) throws ClientProtocolException {
		
		HttpRequestBase httpRequestBase = null;
		
		String httpMethod = requestSettings.getHttpRequestMethod();
		if(httpMethod != null && !"".equals(httpMethod)) {
			switch (httpMethod.toLowerCase()) {
			case "get":
				
				httpRequestBase = new HttpGet(requestSettings.getUrl());				
				break;
				
			case "post":
				
				httpRequestBase = new HttpPost(requestSettings.getUrl());				
				break;
				
			case "delete":
				
//				httpRequestBase = new HttpDelete(requestSettings.getUrl());				
//				break;
				
			case "head":
				
//				httpRequestBase = new HttpHead(requestSettings.getUrl());				
//				break;
				
			case "patch":
				
			case "put":

			default:
				
				logger.error("Rest4sCall ERROR : " + httpMethod + "is not supported yet.");
				throw new ClientProtocolException();
			}
		}
		
		// add query parameter string if it exist
		// TODO : need validator logics... and encoding??
		String queryParam = requestSettings.getQueryParameterString();
		if(requestSettings.getQueryParameterString() != null && !"".equals(requestSettings.getQueryParameterString())) {
			if(queryParam.charAt(0) == '?') {
				
			} else {
				// in a case query parameter string validation fail
				// TODO : thorw Exception??
				logger.error("invalid query parameter string.");
			}
		}
		
		// can add request config options like kinds of timeout
		// currently do nothing...
		RequestConfig requestConfig = RequestConfig.custom().build();
		
		httpRequestBase.setConfig(requestConfig);
		
		// set up headers
		if(requestSettings.getHeaders() != null && !requestSettings.getHeaders().isEmpty()) {
			for(Header header : requestSettings.getHeaders()) {
				httpRequestBase.addHeader(header);
			}			
		}
		
		return httpRequestBase;
	}
	
	public String sendRequest(RequestSettings requestSettings) throws IOException {
		
		HttpUriRequest httpUriRequest = createHttpRequest(requestSettings);
		
		// can add request context options like cookies, ...
		HttpClientContext httpClientContext = HttpClientContext.create();
		httpClientContext.setCookieStore(requestSettings.getCookieStore());

		logger.debug("Executing request" + httpUriRequest.getRequestLine());
		
		String responseBody = null;
		try {
			responseBody = httpclient.execute(httpUriRequest, simpleResponseHandler, httpClientContext);
		} catch (ClientProtocolException e) {
			httpclient.close();
			e.printStackTrace();
			
		} catch (IOException e) {
			httpclient.close();
			e.printStackTrace();
			
		} finally {
			httpclient.close();
		}
		
		return responseBody;		
	}
	
	public String getRequest(String url) throws IOException{
		
		RequestSettings requestSettings = new RequestSettings(url);
		requestSettings.setHttpRequestMethod("get");
		
		return sendRequest(requestSettings);
	}	
	
	public String postRequest(String url) throws IOException{
		
		RequestSettings requestSettings = new RequestSettings(url);
		requestSettings.setHttpRequestMethod("post");
		
		return sendRequest(requestSettings);
	}
	
}
