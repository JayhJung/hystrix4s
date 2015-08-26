package com.hytrix.counterserver.example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hytrix.counterserver.example.dto.Article;
import com.hytrix.counterserver.example.dto.TimeLine;

@RestController
@RequestMapping("countserver/rest")
public class TimeLineController {

	List<Article> articles;
	
	//http://localhost:8080/restserver/rest/httpclient
	//http://localhost:8090/countserver/rest/hello

	public TimeLineController() {
		articles = getDummyArticle();
	}

	@RequestMapping(value = "/hello", method = {RequestMethod.GET})
	public TimeLine getArticles(@RequestParam(defaultValue = "0") int delay) {
		TimeLine tl = new TimeLine();
		tl.setArticleList(articles);
		giveDelay(delay);
		return tl;
	}
	
	private List<Article> getDummyArticle() {
		List<Article> list = new ArrayList<Article>();

		Article article;
		for (int i = 0; i < 10; i++) {
			article = new Article();
			article.setArticleId(i);
			article.setText("Hello my name is CountServer(" + i + ")  !!!!!!!");
			article.setUpdateTime(new Date());
			list.add(article);
		}
		return list;
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
