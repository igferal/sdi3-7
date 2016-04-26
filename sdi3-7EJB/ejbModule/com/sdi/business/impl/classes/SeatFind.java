package com.sdi.business.impl.classes;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;

public class SeatFind {

	private Long[] id;

	public SeatFind(Long[] id) {
		this.id = id;
	}

	public Seat find() {
		return Factories.persistence.newSeatDao().findById(id);
	}

}
