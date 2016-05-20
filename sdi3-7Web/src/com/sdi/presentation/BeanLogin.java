package com.sdi.presentation;

import alb.util.log.Log;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.sdi.business.UserService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

@ManagedBean(name = "login")
@SessionScoped
public class BeanLogin implements Serializable {
	private static final long serialVersionUID = 6L;

	private String name;
	private String password;

	public BeanLogin() {

	}

	public String verify() {
		UserService login = Factories.services.getUserService();
		User user = login.verify(name, password);
		if (user != null) {
			putUserInSession(user);
			Log.info("El usuario [%s] ha iniciado sesion", name);
			return "exito";
		}
		Log.info("El usuario [%s] no esta registrado", name);
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msgs");
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, "", bundle
						.getString("login_result_error")));
		return "fallo";
	}

	public void logout() {
		FacesContext.getCurrentInstance().getExternalContext()
				.invalidateSession();
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("index.xhtml");
		} catch (IOException e) {

		}
	}

	private void putUserInSession(User user) {
		Map<String, Object> session = FacesContext.getCurrentInstance()
				.getExternalContext().getSessionMap();
		session.put("LOGGEDIN_USER", user);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
