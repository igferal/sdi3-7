package com.sdi.presentation;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import com.sdi.business.impl.classes.TripsUpdateStatusTask;

/**
 * Maneja el mantenimiento programado de la base de datos sobre los estados
 * de los viajes (OPEN, CLOSED, CANCELLED, DONE)
 *
 */
@ManagedBean (name = "applicationBean", eager = true)
@ApplicationScoped
public class BeanApplication {

	public BeanApplication() {
		new TripsUpdateStatusTask();
	}
}
