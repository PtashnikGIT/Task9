package com.epam.jsftask.presentation.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.event.ComponentSystemEvent;

import org.apache.log4j.Logger;

import com.epam.jsftask.database.INewsDAO;
import com.epam.jsftask.model.News;
import com.epam.jsftask.presentation.form.NewsForm;
import com.epam.jsftask.util.NewsDateComporator;

import static com.epam.jsftask.resources.Constants.*;

public class NewsManager implements Serializable {

	private final static Logger log = Logger.getLogger(NewsManager.class);
	private static final long serialVersionUID = 1L;
	private INewsDAO newsDAO;
	private NewsForm form;

	public void resetForm(ComponentSystemEvent event) {
		initForm();
	}

	private void initForm() {
		log.info("initForm method");
		List<News> newsList = newsDAO.getList();
		Collections.sort(newsList, new NewsDateComporator());
		form.setNewsList(newsList);
		form.setCheckboxes(new HashMap<Integer, Boolean>());
		form.setCancelPage(LIST_PAGE);
	}

	public String list() {
		log.info("list method");
		initForm();
		return LIST_PAGE;
	}

	public String delete() {
		log.info("delete method");
		List<String> idListToDelete = new ArrayList<String>();
		for (Entry<Integer, Boolean> entry : form.getCheckboxes().entrySet()) {
			if (entry.getValue()) {
				idListToDelete.add(entry.getKey().toString());
			}
		}
		String[] idsToDel = idListToDelete.toArray(new String[idListToDelete
				.size()]);
		newsDAO.remove(idsToDel);
		return LIST_PAGE;
	}

	public String delete(int newsId) {
		log.info("delete method with newsId=" + newsId);
		News news = new News();
		news.setNewsId(newsId);
		newsDAO.remove(news);
		form.setCancelPage(LIST_PAGE);
		return LIST_PAGE;
	}

	public String edit(int newsId) {
		log.info("edit method with newsId=" + newsId);
		News news = newsDAO.fetchById(newsId);
		form.setNews(news);
		return EDIT_PAGE;
	}

	public String view(int newsId) {
		log.info("view method with newsId=" + newsId);
		News news = newsDAO.fetchById(newsId);
		form.setNews(news);
		form.setCancelPage(VIEW_PAGE);
		return VIEW_PAGE;
	}

	public String add() {
		log.info("add method");
		News news = new News();
		news.setNewsDate(new Date());
		form.setNews(news);
		form.setCancelPage(LIST_PAGE);
		return EDIT_PAGE;
	}

	public String save() {
		log.info("save method");
		newsDAO.save(form.getNews());
		form.setCancelPage(VIEW_PAGE);
		return VIEW_PAGE;
	}

	public Boolean isAnyNews() {
		log.info("isAnyNews method");
		if (form.getNewsList().isEmpty()) {
			return false;
		}
		return true;
	}

	public String cancel() {
		return form.getCancelPage();
	}

	public void setNewsDAO(INewsDAO newsDAO) {
		this.newsDAO = newsDAO;
	}

	public NewsForm getForm() {
		return this.form;
	}

	public void setForm(NewsForm form) {
		this.form = form;
	}

}
