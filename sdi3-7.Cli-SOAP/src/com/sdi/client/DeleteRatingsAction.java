package com.sdi.client;

import java.util.List;

import com.sdi.client.printUtil.EntityNotFoundException;
import com.sdi.client.printUtil.PrintUtil;
import com.sdi.ws.EjbRatingServiceService;
import com.sdi.ws.EjbTripServiceService;
import com.sdi.ws.Rating;
import com.sdi.ws.RatingService;
import com.sdi.ws.Trip;
import com.sdi.ws.TripService;

import alb.util.console.Console;
import alb.util.menu.Action;

public class DeleteRatingsAction implements Action {

	@Override
	public void execute() throws Exception {
		RatingService rService = new EjbRatingServiceService().getRatingServicePort();

		List<Rating> ratings = rService.findAll();

		System.out.printf("%s %s %s %s %s %s\n", "_ID_RATING_",
				"_DESTINO__________________________________________",
				"_IDUSER_", "_SOBRE_IDUSER_", "_VALORACION_",
				"_COMENTARIO___________________");

		TripService tServices = new EjbTripServiceService()
				.getTripServicePort();
		Trip trip;

		for (Rating rating : ratings) {
			trip = tServices.findTrip(rating.getSeatFromTripId());
			System.out.printf("%-11s %-50s %-8s %-14s %-12s %-30s\n",
					rating.getId(),
					PrintUtil.formatAddress(trip.getDestination()),
					rating.getSeatFromUserId(), rating.getSeatAboutUserId(),
					rating.getValue(), rating.getComment());
		}

		Long id = Console.readLong("IDRATING a eliminar");

		int result = rService.delete(id);

		if (result == 1)
			Console.println("Rating eliminado correctamente");
		else
			throw new EntityNotFoundException("El Rating no existe...");

	}

}
