package com.sdi.business.impl.classes;

import java.util.Date;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.persistence.SeatDao;

public class SeatMoveToPending {

	public void move(Long idUser, Long idTrip) {
		Trip trip = Factories.services.createTripService().findTrip(idTrip);

		if (trip.getClosingDate().after(new Date())) {
			SeatDao seatDao = Factories.persistence.newSeatDao();
			Seat seat = seatDao.findByUserAndTrip(idUser, idTrip);

			if (seat != null) {
				seatDao.delete(seat.makeKey());
				if (seat.getStatus() != null
						&& seat.getStatus().equals(SeatStatus.ACCEPTED)) {
					incrementAvailablePax(idTrip);
					Log.info(
							"Application sin Seat para el usuario [%s] en el viaje [%s] (desde Accepted)",
							idUser, idTrip);
				} else
					Log.info(
							"Application sin Seat para el usuario [%s] en el viaje [%s] (desde Excluded)",
							idUser, idTrip);
			}
		}
	}

	private void incrementAvailablePax(Long idTrip) {
		Trip trip = Factories.services.createTripService().findTrip(idTrip);
		trip.setAvailablePax(trip.getAvailablePax() + 1);
		Factories.persistence.newTripDao().update(trip);
		Log.debug(
				"Plazas disponibles incrementadas en 1 unidad del viaje [%s]",
				idTrip);
	}
}
