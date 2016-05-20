package com.sdi.presentation;

import java.io.Serializable;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.model.UserStatus;

@ManagedBean(name = "join")
public class BeanJoin implements Serializable {

	private static final long serialVersionUID = 7L;

	private String username;
	private String nombre;
	private String apellidos;
	private String email;
	private String password;
	private String confirmaPassword;

	public BeanJoin() {

	}

	public String createUser() {
		if (!password.equals(confirmaPassword)) {
			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = context.getApplication().getResourceBundle(
					context, "msgs");
			context.addMessage(
					"form:password",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "", bundle
							.getString("join_comparePassword_error")));

			return "fallo";
		}

		User user = new User();
		user.setLogin(username);
		user.setName(nombre);
		user.setSurname(apellidos);
		user.setEmail(email);
		user.setPassword(password);
		user.setStatus(UserStatus.ACTIVE);

		if (Factories.services.getUserService().saveUser(user)) {
			Log.debug("Se ha registrado con éxito al usuario [%s]", username);

			FacesContext context = FacesContext.getCurrentInstance();
			ResourceBundle bundle = context.getApplication().getResourceBundle(
					context, "msgs");
			context.addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "", bundle
							.getString("join_result_exito").concat(" ")
							.concat(username).concat(". ")
							.concat(bundle.getString("join_result_exito2"))));

			return "exito";
		}

		return "fallo";
	}

	public void checkExisteUsuario(FacesContext context,
			UIComponent componentToValidate, Object value)
			throws ValidatorException {
		if (Factories.persistence.newUserDao().findByLogin((String) value) != null) {
			ResourceBundle bundle = context.getApplication().getResourceBundle(
					context, "msgs");
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_WARN, "",
					bundle.getString("join_error_existe_username")));
		}
	}

	public void validateEmail(FacesContext context,
			UIComponent componentToValidate, Object value)
			throws ValidatorException {
		if (value != null) {
			Pattern pattern = Pattern
					.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
			if (!pattern.matcher(value.toString()).matches()) {
				ResourceBundle bundle = context.getApplication()
						.getResourceBundle(context, "msgs");
				throw new ValidatorException(new FacesMessage(
						FacesMessage.SEVERITY_ERROR, "",
						bundle.getString("join_invalid_email")));
			}
		}
	}

	public String createAccount() {
		return "fallo";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmaPassword() {
		return confirmaPassword;
	}

	public void setConfirmaPassword(String confirmaPassword) {
		this.confirmaPassword = confirmaPassword;
	}

}
