package com.sdi.business.impl.classes;

import java.util.Date;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.Trip;

public class ApplicationSave {

	public boolean save(Application application) {
		Trip trip = Factories.services.getTripService().findTrip(
				application.getTripId());

		if (trip.getClosingDate().before(new Date()))
			return false;

		Factories.persistence.newApplicationDao().save(application);
		Log.info("Application creada para el usuario [%s] en el viaje [%s]",
				application.getUserId(), application.getTripId());

		return true;
	}

}
