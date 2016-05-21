package com.sdi.client;

import java.util.List;

import com.sdi.business.RatingService;
import com.sdi.business.TripService;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Rating;
import com.sdi.model.Trip;

import alb.util.console.Console;
import alb.util.menu.Action;

public class DeleteRatingsAction implements Action {

	@Override
	public void execute() throws Exception {
		RatingService rService = Factories.services.getRatingService();
		
		List<Rating> ratings = rService.findAll();
		
		System.out.printf("%s %s %s %s %s %s\n",
				"_ID_RATING_",
				"_DESTINO__________________________________________",
				"_IDUSER_",
				"_SOBRE_IDUSER_",
				"_VALORACION_",
				"_COMENTARIO___________________");
		
		TripService tServices = Factories.services.getTripService();
		Trip trip;
		
		for (Rating rating:ratings) {
			trip = tServices.findTrip(rating.getSeatFromTripId());
			System.out.printf("%-11s %-50s %-8s %-14s %-12s %-30s\n",
					rating.getId(),
					trip.getDestination().escapeToConsole(),
					rating.getSeatFromUserId(),
					rating.getSeatAboutUserId(),
					rating.getValue(),
					rating.getComment());
		}
		
		Long id = Console.readLong("IDRATING a eliminar");
		
		int result = rService.delete(id);
		
		if (result == 1)
			Console.println("Rating eliminado correctamente");
		else
			throw new EntityNotFoundException("El Rating no existe...");
	}

}
