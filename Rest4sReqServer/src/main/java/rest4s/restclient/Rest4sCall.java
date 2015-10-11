package rest4s.restclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Rest4sCall {
	
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
	
	public String GetRequest(String url) throws Exception{
		HttpGet httpGet = new HttpGet(url);
		System.out.println("Executing request " + httpGet.getRequestLine());
		String responseBody = null;
		try {
			responseBody = httpclient.execute(httpGet, simpleResponseHandler);
		} catch (ClientProtocolException e) {
			httpclient.close();
			e.printStackTrace();
		} catch (IOException e) {
			httpclient.close();
			e.printStackTrace();
		}finally{
			System.out.println("----------------------------------------");
//			System.out.println(responseBody);
		}
		return responseBody;
	}
	
	
	public String PostRequest(String url) throws Exception{
		HttpPost httpPost = new HttpPost(url);
		System.out.println("Executing request " + httpPost.getRequestLine());
		String responseBody = null;
		try {
			responseBody = httpclient.execute(httpPost, simpleResponseHandler);
		} catch (ClientProtocolException e) {
			httpclient.close();
			e.printStackTrace();
		} catch (IOException e) {
			httpclient.close();
			e.printStackTrace();
		}finally{
			System.out.println("----------------------------------------");
			System.out.println(responseBody);
		}
		return responseBody;
	}
}
