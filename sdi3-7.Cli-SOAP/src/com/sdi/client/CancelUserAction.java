package com.sdi.client;

import java.util.List;

import alb.util.console.Console;
import alb.util.menu.Action;
import com.sdi.client.printUtil.EntityNotFoundException;
import com.sdi.ws.EjbSeatServiceService;
import com.sdi.ws.EjbTripServiceService;
import com.sdi.ws.EjbUserServiceService;
import com.sdi.ws.Seat;
import com.sdi.ws.SeatService;
import com.sdi.ws.Trip;
import com.sdi.ws.TripService;
import com.sdi.ws.User;
import com.sdi.ws.UserService;
import com.sdi.ws.UserStatus;

public class CancelUserAction implements Action {

	@Override
	public void execute() throws Exception {
		Long id = Console.readLong("IDUSER a deshabilitar");

		UserService userService = new EjbUserServiceService()
				.getUserServicePort();
		User user = userService.findById(id);

		assertNotNull(user);
		assertActive(user);

		SeatService sServices = new EjbSeatServiceService()
				.getSeatServicePort();
		TripService tServices = new EjbTripServiceService()
				.getTripServicePort();
		List<Seat> seats = sServices.findByUserAndNotExcludedAndOpenTrip(user
				.getId());
		Trip trip;

		for (Seat seat : seats) {
			trip = tServices.findTrip(seat.getTripId());
			sServices.moveSeatToExcluded(seat);
			trip.setAvailablePax(trip.getAvailablePax() + 1);
			tServices.update(trip);
		}

		user.setStatus(UserStatus.CANCELLED);
		userService.updateUser(user);
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
		throw new EntityNotFoundException("El usuario no existe...");

	}

}
