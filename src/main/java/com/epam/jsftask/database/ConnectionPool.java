package com.epam.jsftask.database;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

final class ConnectionPool implements Serializable {

	private static final long serialVersionUID = 1L;
	private String driver;
	private String url;
	private String user;
	private String pass;
	private int poolSize;
	private BlockingQueue<Connection> connectionQueue;
	private static final Logger log = Logger.getLogger(ConnectionPool.class);

	public void init() {
		Locale.setDefault(Locale.ENGLISH); // cannot create connection without
											// it
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			log.error(e);
		}

		connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
		for (int i = 0; i < poolSize; i++) {
			Connection conn = null;
			try {
				conn = DriverManager.getConnection(url, user, pass);
			} catch (SQLException e) {
				log.error(e);
			}
			connectionQueue.offer(conn);
		}
	}

	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = connectionQueue.take();
		} catch (InterruptedException e) {
			log.error(e);
		}
		return connection;
	}

	public void closeConnection(Connection connection) {
		boolean closed = true;
		try {
			closed = connection.isClosed();
		} catch (SQLException e) {
			log.error(e);
		}
		if (!closed) {
			if (!connectionQueue.offer(connection)) {
				log.warn("Connection didn't return to pool. Probably it is missed.");
			}
		} else {
			log.warn("Trying to return connection.");
		}
	}

	public void dispose() {
		Connection connection;
		while ((connection = connectionQueue.poll()) != null) {
			try {
				if (!connection.getAutoCommit()) {
					connection.commit();
				}
				connection.close();
			} catch (SQLException e) {
				log.error(e);
			}
		}
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public void setPoolSize(int poolSize) {
		this.poolSize = poolSize;
	}
}
