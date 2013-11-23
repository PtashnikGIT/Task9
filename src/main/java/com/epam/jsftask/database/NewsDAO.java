package com.epam.jsftask.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.jsftask.model.News;

public final class NewsDAO implements INewsDAO, Serializable {

	private static final long serialVersionUID = 1L;
	private ConnectionPool pool;
	private static final String SQL_NEWS_SELECT_ALL = "SELECT * FROM NEWS";
	private static final String SQL_NEWS_INSERT = "INSERT INTO NEWS(NEWSTITLE, NEWSDATE, NEWSBRIEF, NEWSCONTENT)"
			+ "VALUES (?, ?, ?, ?)";
	private static final String SQL_NEWS_DELETE = "DELETE FROM NEWS WHERE NEWSID=?";
	private static final String SQL_NEWS_SELECT_BY_ID = "SELECT * FROM NEWS WHERE NEWSID=?";
	private static final String SQL_NEWS_UPDATE = "UPDATE NEWS SET NEWSTITLE=?, NEWSDATE=?, NEWSBRIEF=?,"
			+ " NEWSCONTENT=?" + " WHERE NEWSID=?";
	private static final String SQL_STATEMENT_SELECT_SEQ_CURRVAL = "select NEWS_SEQ.currval from NEWS";
	private static final String SEQUENCE_CURRVAL = "currval";

	private static final Logger log = Logger.getLogger(NewsDAO.class);

	@Override
	public List<News> getList() {
		List<News> newsList = new ArrayList<News>();
		Statement statement;
		ResultSet resultSet;
		News news = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			statement = connection.createStatement();
			resultSet = statement.executeQuery(SQL_NEWS_SELECT_ALL);

			while (resultSet.next()) {
				news = new News();
				news.setNewsId(resultSet.getInt(1));
				news.setNewsTitle(resultSet.getString(2));
				java.util.Date utilDate = new java.util.Date(resultSet.getDate(
						3).getTime());
				news.setNewsDate(utilDate);
				news.setNewsBrief(resultSet.getString(4));
				news.setNewsContent(resultSet.getString(5));
				newsList.add(news);
				news = null;
				utilDate = null;
			}
		} catch (SQLException e) {
			log.error(e);

		} finally {
			pool.closeConnection(connection);
		}
		return newsList;
	}

	@Override
	public News save(News news) {
		Connection connection = null;
		log.debug("news, came to db for saving/updating: " + news.toString());
		try {
			connection = pool.getConnection();
			log.debug("in try in dao save");
			if (news.getNewsId() > 0) {
				updateNews(news, connection);
			} else {
				insertNews(news, connection);
			}
		} catch (SQLException e) {
			log.error(e);

		} finally {
			pool.closeConnection(connection);
		}
		return news;
	}

	@Override
	public void remove(News news) {
		PreparedStatement ps = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			ps = connection.prepareStatement(SQL_NEWS_DELETE);
			ps.setInt(1, news.getNewsId());
			ps.executeUpdate();
		} catch (SQLException e) {
			log.debug(e);

		} finally {
			pool.closeConnection(connection);
		}
	}

	public void remove(String[] ids) {
		PreparedStatement ps = null;
		Connection connection = null;
		try {
			connection = pool.getConnection();
			for (String strId : ids) {
				ps = connection.prepareStatement(SQL_NEWS_DELETE);
				ps.setInt(1, Integer.parseInt(strId));
				ps.executeUpdate();
			}

		} catch (SQLException e) {
			log.debug(e);

		} finally {
			pool.closeConnection(connection);
		}
	}

	@Override
	public News fetchById(int id) {
		PreparedStatement ps = null;
		Connection connection = null;
		News news = null;
		try {
			connection = pool.getConnection();
			ps = connection.prepareStatement(SQL_NEWS_SELECT_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();

			news = new News();

			while (rs.next()) {
				news.setNewsId(rs.getInt(1));
				news.setNewsTitle(rs.getString(2));

				java.util.Date utilDate = new java.util.Date(rs.getDate(3)
						.getTime());
				news.setNewsDate(utilDate);

				news.setNewsBrief(rs.getString(4));
				news.setNewsContent(rs.getString(5));
			}

		} catch (SQLException e) {
			log.error("Error in fetch");

		} finally {
			pool.closeConnection(connection);
		}
		return news;
	}

	private void updateNews(News news, Connection connection)
			throws SQLException {
		PreparedStatement ps = connection.prepareStatement(SQL_NEWS_UPDATE);
		proccesPreparedStatement(ps, news);
		log.debug("update done!!!");
	}

	private void insertNews(News news, Connection connection)
			throws SQLException {

		PreparedStatement ps = connection.prepareStatement(SQL_NEWS_INSERT);
		proccesPreparedStatement(ps, news);
		Statement st = connection.createStatement();
		ResultSet rs = null;
		rs = st.executeQuery(SQL_STATEMENT_SELECT_SEQ_CURRVAL);
		if (rs.next()) {
			news.setNewsId(rs.getInt(SEQUENCE_CURRVAL));
			log.debug("set id to news");
		}
	}

	private void proccesPreparedStatement(PreparedStatement ps, News news)
			throws SQLException {
		ps.setString(1, news.getNewsTitle());
		ps.setDate(2, new java.sql.Date(news.getNewsDate().getTime()));
		ps.setString(3, news.getNewsBrief());
		ps.setString(4, news.getNewsContent());

		if (news.getNewsId() > 0) {
			ps.setInt(5, news.getNewsId());
		}
		ps.executeUpdate();
	}

	public ConnectionPool getPool() {
		return pool;
	}

	public void setPool(ConnectionPool pool) {
		this.pool = pool;

	}

}