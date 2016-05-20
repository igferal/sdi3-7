package com.sdi.model;

import java.util.ResourceBundle;

import javax.faces.context.FacesContext;

public enum TripStatus {
	OPEN,
	CLOSED,
	CANCELLED,
	DONE;
	
	@Override
	public String toString() {
		FacesContext context = FacesContext.getCurrentInstance();
		ResourceBundle bundle = context.getApplication().getResourceBundle(
				context, "msgs");
		
		switch (this) {
			case OPEN: return bundle.getString("abierto");
			case CLOSED: return bundle.getString("cerrado");
			case CANCELLED: return bundle.getString("cancelado");
			case DONE: return bundle.getString("realizado");
			default: return null;
		}
	}

}
