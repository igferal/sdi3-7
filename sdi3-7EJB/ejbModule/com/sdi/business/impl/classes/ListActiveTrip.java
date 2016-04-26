package com.sdi.business.impl.classes;

import java.util.Date;
import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;

public class ListActiveTrip {

	private Date date;

	public ListActiveTrip(Date date) {
		this.date = date;
	}

	public List<Trip> listActiveTrips() {

		return Factories.persistence.newTripDao().findTravelsAfter(date);
	}

}
