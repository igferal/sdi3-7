package com.sdi.business.impl.classes;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;

public class TripFind {

	public Trip findById(Long id) {
		return Factories.persistence.newTripDao().findById(id);
	}

	public List<Trip> findTakePartOf(Long id) {
		return Factories.persistence.newTripDao().findTripsTakePartOf(id);
	}

	public List<Trip> findAcceptedUser(Long id) {
		return Factories.persistence.newTripDao().findAcceptedUser(id);
	}

}
