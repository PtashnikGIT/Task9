package com.epam.jsftask.util;

import java.util.Comparator;

import com.epam.jsftask.model.News;

public final class NewsDateComporator implements Comparator<News> {

	@Override
	public int compare(News o1, News o2) {

		return (int) ((o2.getNewsDate().getTime() + o2.getNewsId()) - (o1
				.getNewsDate().getTime() + o1.getNewsId()));
	}

}
