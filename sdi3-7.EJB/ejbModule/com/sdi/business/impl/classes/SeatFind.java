package com.sdi.business.impl.classes;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;

public class SeatFind {

	private Long[] id;

	public SeatFind(Long[] id) {
		this.id = id;
	}

	public SeatFind() {
		
	}

	public Seat find() {
		return Factories.persistence.newSeatDao().findById(id);
	}

	public List<Seat> findByUserAndNotExcluded(Long id) {
		return Factories.persistence.newSeatDao().findByUserAndNotExcluded(id);
	}

}
