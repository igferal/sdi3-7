package com.sdi.business.impl.classes;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;

public class findAcceptedByTrip {

	private Long idTrip;

	public findAcceptedByTrip(Long idTrip) {

		this.idTrip = idTrip;

	}

	public List<Seat> listAcceptedByTrip() {
		return Factories.persistence.newSeatDao().findAcceptedByTrip(idTrip);
	}

}
