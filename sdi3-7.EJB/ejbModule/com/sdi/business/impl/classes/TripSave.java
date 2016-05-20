package com.sdi.business.impl.classes;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.persistence.TripDao;

public class TripSave {

	private Trip trip;

	public TripSave(Trip trip) {

		this.trip = trip;
	}

	public void save() {
		TripDao tdao = Factories.persistence.newTripDao();
		tdao.save(trip);
		Log.info("Viaje [%s] creado", trip.getId());
	}

}
