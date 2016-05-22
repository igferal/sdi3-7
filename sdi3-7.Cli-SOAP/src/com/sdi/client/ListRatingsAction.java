package com.sdi.client;

import java.util.List;

import com.sdi.client.printUtil.PrintUtil;
import com.sdi.ws.EjbRatingServiceService;
import com.sdi.ws.EjbTripServiceService;
import com.sdi.ws.Rating;
import com.sdi.ws.Trip;
import com.sdi.ws.TripService;

import alb.util.menu.Action;

public class ListRatingsAction implements Action {

	@Override
	public void execute() throws Exception {
		List<Rating> ratings = new EjbRatingServiceService()
				.getRatingServicePort().findLastMonthDone();

		System.out.printf("%s %s %s %s %s\n",
				"_DESTINO__________________________________________",
				"_IDUSER_", "_SOBRE_IDUSER_", "_VALORACION_",
				"_COMENTARIO___________________");

		TripService tServices = new EjbTripServiceService()
				.getTripServicePort();
		Trip trip;

		for (Rating rating : ratings) {
			trip = tServices.findTrip(rating.getSeatFromTripId());
			System.out.printf("%-50s %-8s %-14s %-12s %-30s\n",
					PrintUtil.formatAddress(trip.getDestination()),
					rating.getSeatFromUserId(), rating.getSeatAboutUserId(),
					rating.getValue(), rating.getComment());
		}
	}

}
