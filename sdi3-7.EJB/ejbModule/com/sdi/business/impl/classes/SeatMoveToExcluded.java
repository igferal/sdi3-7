package com.sdi.business.impl.classes;

import java.util.Date;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.model.SeatStatus;
import com.sdi.model.Trip;
import com.sdi.persistence.SeatDao;

public class SeatMoveToExcluded {

	public void exclude(Long idUser, Long idTrip) {
		Trip trip = Factories.services.getTripService().findTrip(idTrip);

		if (trip.getClosingDate().after(new Date())) {
			SeatDao seatDao = Factories.persistence.newSeatDao();
			Seat seat = seatDao.findByUserAndTrip(idUser, idTrip);

			if (seat == null) {
				seat = new Seat();
				seat.setUserId(idUser);
				seat.setTripId(idTrip);
				seat.setStatus(SeatStatus.EXCLUDED);
				seatDao.save(seat);
				Log.info(
						"Seat Excluded para el usuario [%s] en el viaje [%s] (desde Pending)",
						idUser, idTrip);
			} else if (seat.getStatus().equals(SeatStatus.ACCEPTED)) {
				seat.setStatus(SeatStatus.EXCLUDED);
				seatDao.update(seat);
				incrementAvailablePax(idTrip);
				Log.info(
						"Seat Excluded para el usuario [%s] en el viaje [%s] (desde Accepted)",
						idUser, idTrip);
			}
		}

	}

	private void incrementAvailablePax(Long idTrip) {
		Trip trip = Factories.services.getTripService().findTrip(idTrip);
		trip.setAvailablePax(trip.getAvailablePax() + 1);
		Factories.persistence.newTripDao().update(trip);
		Log.debug(
				"Plazas disponibles incrementadas en 1 unidad del viaje [%s]",
				idTrip);
	}

}
