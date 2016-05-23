package com.sdi.client.action;

import java.util.List;

import com.sdi.business.TripService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Rating;
import com.sdi.model.Trip;

import alb.util.menu.Action;

public class ListRatingsAction implements Action {

	@Override
	public void execute() throws Exception {
		List<Rating> ratings = Factories.services.getRatingService().findLastMonthDone();
		
		System.out.printf("%s %s %s %s %s\n",
				"_DESTINO__________________________________________",
				"_IDUSER_",
				"_SOBRE_IDUSER_",
				"_VALORACION_",
				"_COMENTARIO___________________");
		
		TripService tServices = Factories.services.getTripService();
		Trip trip;
		
		for (Rating rating:ratings) {
			trip = tServices.findTrip(rating.getSeatFromTripId());
			System.out.printf("%-50s %-8s %-14s %-12s %-30s\n",
					trip.getDestination().escapeToConsole(),
					rating.getSeatFromUserId(),
					rating.getSeatAboutUserId(),
					rating.getValue(),
					rating.getComment());
		}
	}

}
