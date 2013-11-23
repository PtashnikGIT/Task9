package com.epam.jsftask.database;
import java.util.List;

import com.epam.jsftask.model.News;

public interface INewsDAO {
	List<News> getList();
	News save(News news);
	void remove(News news);
	void remove(String[] ids);
	News fetchById(int newsId);
}
