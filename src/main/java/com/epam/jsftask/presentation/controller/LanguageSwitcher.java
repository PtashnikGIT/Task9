package com.epam.jsftask.presentation.controller;

import java.io.Serializable;
import java.util.Locale;

import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

public class LanguageSwitcher implements Serializable {
	private static final long serialVersionUID = 2756934361134603857L;
    private static final Logger log = Logger.getLogger(LanguageSwitcher.class.getName());
   
    private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return locale.getLanguage();
    }

    public void changeLanguage(String language) {
    	log.info("changeLanguage method: " + language);
        locale = new Locale(language);
        FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
    }
    
}
