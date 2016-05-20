package com.sdi.business.impl.classes;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;

public class ListPromoterTrips {

	private Long id;

	public ListPromoterTrips(Long id) {
		this.id = id;
	}

	public List<Trip> listTrips() {
		// TODO Auto-generated method stub
		return Factories.persistence.newTripDao().dateAscendingPromoter(id);
	}
}
