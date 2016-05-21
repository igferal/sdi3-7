package com.sdi.business;

import java.util.List;

import com.sdi.model.Seat;

public interface SeatService {

	void moveToAccepted(Long idUser, Long idTrip);
	void moveToPending(Long idUser, Long idTrip);
	void moveToExcluded(Long idUser, Long idTrip);
	Seat find(Long[] id);
	List<Seat> findByUserAndNotExcludedAndOpenTrip(Long id);
	void moveSeatToExcluded(Seat seat);
}
