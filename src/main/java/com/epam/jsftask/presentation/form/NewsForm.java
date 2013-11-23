package com.epam.jsftask.presentation.form;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.epam.jsftask.model.News;

public class NewsForm implements Serializable {

	private final static Logger log = Logger.getLogger(NewsForm.class);
	private static final long serialVersionUID = 1L;
	private News news;
	private List<News> newsList;
	private Map<Integer, Boolean> checkboxes;
	private String cancelPage;
	
	
	public News getNews() {
		return news;
	}

	public void setNews(News news) {
		this.news = news;
	}

	public List<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(List<News> newsList) {
		this.newsList = newsList;
	}

	public Map<Integer, Boolean> getCheckboxes() {
		return checkboxes;
	}

	public void setCheckboxes(Map<Integer, Boolean> hashMap) {
		this.checkboxes = hashMap;
	}

	public String getCancelPage() {
		return cancelPage;
	}

	public void setCancelPage(String cancelPage) {
		this.cancelPage = cancelPage;
		log.info("cancel page is:" + cancelPage);
	}
	
}
