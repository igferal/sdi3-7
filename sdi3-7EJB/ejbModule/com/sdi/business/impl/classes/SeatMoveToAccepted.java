package com.sdi.business.impl.classes;

import java.util.Date;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.persistence.SeatDao;

public class SeatMoveToAccepted {

	public void accept(Long idUser, Long idTrip) {
		Trip trip = Factories.services.createTripService().findTrip(idTrip);

		if (trip.getClosingDate().after(new Date())
				&& trip.getAvailablePax() > 0) {

			SeatDao seatDao = Factories.persistence.newSeatDao();
			Seat seat = seatDao.findByUserAndTrip(idUser, idTrip);

			if (seat == null) {
				seat = new Seat();
				seat.setUserId(idUser);
				seat.setTripId(idTrip);
				seat.setStatus(SeatStatus.ACCEPTED);
				seatDao.save(seat);
				decrementAvailablePax(idTrip);
				Log.info("Seat Accepted para el usuario [%s] en el viaje [%s] (desde Pending)", idUser, idTrip);
			} else if (seat.getStatus().equals(SeatStatus.EXCLUDED)) {
				seat.setStatus(SeatStatus.ACCEPTED);
				seatDao.update(seat);
				decrementAvailablePax(idTrip);
				Log.info("Seat Accepted para el usuario [%s] en el viaje [%s] (desde Excluded)", idUser, idTrip);
			}
		}
	}

	private void decrementAvailablePax(Long idTrip) {
		Trip trip = Factories.services.createTripService().findTrip(idTrip);
		trip.setAvailablePax(trip.getAvailablePax() - 1);
		Factories.persistence.newTripDao().update(trip);
		Log.debug("Plazas disponibles decrementadas en 1 unidad del viaje [%s]", idTrip);
	}

}
