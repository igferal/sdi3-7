package com.sdi.business.impl.classes;

import java.util.Date;
import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;

public class ListCurrentPromoterTrips {

	private Long id;

	private Date date;

	public ListCurrentPromoterTrips(Long id, Date date) {

		this.id = id;
		this.date = date;

	}

	public List<Trip> listTrips() {
		return Factories.persistence.newTripDao().findTripsPromotedCurrently(
				id, date);
	}

}
