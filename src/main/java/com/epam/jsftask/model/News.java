package com.epam.jsftask.model;

import java.io.Serializable;
import java.util.Date;

public final class News implements Serializable {

	private static final long serialVersionUID = 7425617925200576020L;
	private int newsId;
	private String newsTitle;
	private Date newsDate;
	private String newsBrief;
	private String newsContent;

	public int getNewsId() {
		return newsId;
	}

	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public Date getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(Date newsDate) {
		this.newsDate = newsDate;
	}

	public String getNewsBrief() {
		return newsBrief;
	}

	public void setNewsBrief(String newsBrief) {
		this.newsBrief = newsBrief;
	}

	public String getNewsContent() {
		return newsContent;
	}

	public void setNewsContent(String newsContent) {
		this.newsContent = newsContent;
	}

	public String toString() {
		return "News [newsId=" + newsId + ", newsTitle=" + newsTitle
				+ ", newsDate=" + newsDate + ", newsBrief=" + newsBrief
				+ ", newsContent=" + newsContent + "]";
	}
}
