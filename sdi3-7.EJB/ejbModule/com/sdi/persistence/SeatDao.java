package com.sdi.persistence;

import java.util.List;

import com.sdi.model.Seat;
import com.sdi.persistence.util.GenericDao;

public interface SeatDao extends GenericDao<Seat, Long[]> {

	Seat findByUserAndTrip(Long userId, Long tripId);
	List<Seat> findByTripId(Long idTrip);
	List<Seat> findByUserAndNotExcluded(Long id);

}
