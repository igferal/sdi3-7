package com.sdi.client;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;

import com.sdi.business.SeatService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.TripService;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Seat;
import com.sdi.model.Trip;
import com.sdi.model.User;
import com.sdi.model.UserStatus;

public class CancelUserAction implements Action {

	@Override
	public void execute() throws Exception {
		Long id = Console.readLong("IDUSER a deshabilitar");
		
		ServicesFactory services = Factories.services;
		
		User user = services.getUserService().findById(id);
		
		assertNotNull(user);
		assertActive(user);
		
		SeatService sServices = services.getSeatService();
		TripService tServices = services.getTripService();
		List<Seat> seats = sServices.findByUserAndNotExcludedAndOpenTrip(user.getId());
		Trip trip;
		
		for (Seat seat:seats) {
			trip = tServices.findTrip(seat.getTripId());
			sServices.moveSeatToExcluded(seat);
			trip.setAvailablePax(trip.getAvailablePax() + 1);
			tServices.update(trip);
		}
		
		user.setStatus(UserStatus.CANCELLED);
		services.getUserService().updateUser(user);
		Console.println("Usuario deshabilitado correctamente");

	}

	private void assertNotNull(User user) throws EntityNotFoundException {
		if (user != null) {
			return;
		}
		
		throw new EntityNotFoundException("El usuario no existe...");
	}
	
	private void assertActive(User user) throws EntityNotFoundException {
		if (user.getStatus().equals(UserStatus.ACTIVE)) {
			return;
		}
		
		throw new EntityNotFoundException("El usuario ya estaba deshabilitado...");
	}

}
