package com.example;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.springframework.boot.test.TestRestTemplate.HttpClientOption;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.Article;
import com.example.dto.TimeLine;
import com.example.hystrix.HolydayRestClient;
import com.example.hystrix.HolydayRestEdited;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@RestController
@RequestMapping("restserver/rest")
public class TimeLineController {

	List<Article> articles;
	String ip = "localhost";
	String port = "8090";
	HttpClient client = new HttpClient();

	public TimeLineController() {
		articles = getDummyArticle();
	}

	@RequestMapping(value = "/httpclient", method = {RequestMethod.GET})
	public TimeLine getBreeefArticles(@RequestParam(defaultValue = "0") int delay) {
		TimeLine tl = new TimeLine();
		tl.setArticleList(articles);
		this.callCounterServer();  //CounterServer 호출 
		return tl;
	}
	
	@RequestMapping(value = "/holidayclient", method = {RequestMethod.GET})
	public TimeLine getDetailArticles(@RequestParam(defaultValue = "0") int delay) {
		TimeLine tl = new TimeLine();
		tl.setArticleList(articles);
		
		this.callCounterServerViaHystrix();		//CounterServer 호출 

		
		
		return tl;
	}
	
	
	private void callCounterServer() {
		String url  = "http://"+ip+":" +port+"/countserver/rest/hello";
		
		
		try {
			Unirest.get(url).header("accept", "application/json")
			.queryString("delay", 3).asJson();
		} catch (UnirestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("##Normal Client called:" + url );
		
		/*GetMethod method = new GetMethod(url);		 
	    method.setRequestHeader("Content-Type","application/x-www-form-urlencoded; charset=utf-8");
	    try {
			client.executeMethod(method);
			System.out.println(method.getResponseBody()+"=================") ;			
		} catch (HttpException e) {
			System.out.println("HttpException ");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException ");
			e.printStackTrace();
		}finally{
			method.releaseConnection();
		}*/
		
	}
	
	private void callCounterServerViaHystrix() {
		String url  = "http://"+ip+":" +port+"/countserver/rest/hello?delay=3";
		System.out.println("##Hystrix Client called:" + url );
    	HolydayRestClient client = HolydayRestEdited.get(url);
    	client = client.header("accept", "application/json");
    	
    	HttpResponse<JsonNode> reponse = HolydayRestEdited.get(url)
    			.header("accept", "application/json")
    			.sendRequest();
	}

	@RequestMapping(value = "/", method = {RequestMethod.GET})
	public TimeLine getArticles(@RequestParam(defaultValue = "0") int delay) {
		TimeLine tl = new TimeLine();
		tl.setArticleList(articles);
		giveDelay(delay);
		return tl;
	}

	@RequestMapping(value = "/{id}", method = {RequestMethod.GET})
	public Article getArticle(@PathVariable long id, @RequestParam(defaultValue = "0") int delay) {

		Article article = new Article();
		article.setArticleId(id);
		article.setText("Hello Spring boot!!!!");
		article.setUpdateTime(new Date());
		System.out.println("Call received as id: " + id);
		
		giveDelay(delay);

		return article;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public long deleteArticle(@PathVariable long id) {
		
		for(Article item : articles)
		{
			if(item.getArticleId() == id){
				articles.remove(item);	
			}
		}
		return id;

	}
	
	@RequestMapping(value="/", method = RequestMethod.POST)
	private long createArticle(@RequestBody Article article){
		System.out.println("######## create an Article ###########");
		article.setArticleId(getMaxId());
		article.setUpdateTime(new Date());
		articles.add(article);
		return article.getArticleId();
		
	}
	
	private List<Article> getDummyArticle() {
		List<Article> list = new ArrayList<Article>();

		Article article;
		for (int i = 0; i < 10; i++) {
			article = new Article();
			article.setArticleId(i);
			article.setText("Hello my name is Steve(" + i + ")");
			article.setUpdateTime(new Date());
			list.add(article);
		}
		return list;
	}
	
	private long getMaxId(){
		
		long id = 0;
		for(Article item: articles){
			if(item.getArticleId() > id){
				id = item.getArticleId();
			}
		}
		return ++id;
	}
	
	private void giveDelay(int sec){
		
			try {
				for(int i = 0; i < sec ; i ++){
					Thread.sleep(1000);
					System.out.println("#" + (i+1) + " sec has elapsed");
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}	
}
