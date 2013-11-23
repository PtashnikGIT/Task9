package com.epam.jsftask.util;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.PropertyConfigurator;

public final class Log4jInitListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent context) {
	}

	@Override
	public void contextInitialized(ServletContextEvent contextEvent) {
		String home = contextEvent.getServletContext().getRealPath("/");
		File properties = new File(home, "WEB-INF/log4j.properties");
		PropertyConfigurator.configure(properties.toString());
		
	}

}
