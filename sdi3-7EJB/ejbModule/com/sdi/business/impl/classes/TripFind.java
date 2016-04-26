package com.sdi.business.impl.classes;

import com.sdi.infrastructure.Factories;

public class TripFind {

	public com.sdi.model.Trip findById(Long id) {
		return Factories.persistence.newTripDao().findById(id);
	}

}
