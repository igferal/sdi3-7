package com.sdi.business;

import com.sdi.model.Seat;

public interface SeatService {

	void moveToAccepted(Long idUser, Long idTrip);
	void moveToPending(Long idUser, Long idTrip);
	void moveToExcluded(Long idUser, Long idTrip);
	Seat find(Long[] id);
}
