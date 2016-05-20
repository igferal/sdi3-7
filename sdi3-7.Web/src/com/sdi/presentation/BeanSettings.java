package com.sdi.presentation;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

@ManagedBean(name = "settings")
@SessionScoped
public class BeanSettings implements Serializable {
	private static final long serialVersionUID = 2L;

	private Map<String, Locale> locales = new HashMap<>();
	private Map<String, String> languages = new HashMap<>();
	private String localeSelected;
	private Locale locale;

	@PostConstruct
	public void init() {
		loadProperties();
		chargeLanguages();
	}

	public Locale getLocale() {
		// Idioma del navegador la primera vez que se accede a getLocale()
		if (locale == null)
			locale = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestLocale();

		// En caso de que no haya fichero de properties para el idioma del
		// navegador, se establece español por defecto
		if (!locales.containsKey(locale.getLanguage()))
			locale = new Locale("es");

		return (locale);
	}

	private void loadProperties() {
		File folder = new File(FacesContext.getCurrentInstance()
				.getExternalContext().getRealPath("/WEB-INF/classes"));

		for (File file : folder.listFiles())
			if (file.isFile() && file.getName().startsWith("messages")
					&& file.getName().contains("_")
					&& file.getName().endsWith(".properties")) {
				locales.put(
						file.getName().split("_")[1].split("\\.")[0],
						new Locale(file.getName().split("_")[1].split("\\.")[0]));
			}

	}

	private void chargeLanguages() {
		languages.clear();
		String display;
		for (Locale locale : locales.values()) {
			display = locale.getDisplayLanguage(getLocale());
			display = display.substring(0, 1).toUpperCase()
					.concat(display.substring(1, display.length()));
			languages.put(display, locale.getLanguage());
		}
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Map<String, Locale> getLocales() {
		return locales;
	}

	public void setLocales(Map<String, Locale> locales) {
		this.locales = locales;
	}

	public void onCountryChange() {
		locale = locales.get(languages.get(localeSelected));
		FacesContext.getCurrentInstance().getViewRoot().setLocale(locale);
		chargeLanguages();
	}

	public String getLocaleSelected() {
		return localeSelected;
	}

	public void setLocaleSelected(String localeSelected) {
		this.localeSelected = localeSelected;
	}

	public Map<String, String> getLanguages() {
		return languages;
	}

	public void setLanguages(Map<String, String> languages) {
		this.languages = languages;
	}

}
