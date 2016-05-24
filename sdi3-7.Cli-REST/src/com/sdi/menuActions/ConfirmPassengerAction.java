package com.sdi.menuActions;

import java.util.List;

import com.sdi.Main;
import com.sdi.model.Trip;
import com.sdi.model.User;

import alb.util.console.Console;
import alb.util.menu.Action;

public class ConfirmPassengerAction implements Action {

	private List<User> usersToConfirm;

	@Override
	public void execute() throws Exception {

		long selectedTrip = -1L;

		while (!isTrip(selectedTrip)) {
			selectedTrip = Console
					.readLong("Escoja el viaje para el que quiere confirmar"
							+ " usuarios mediante su ID, inserte 0 si quiere detener la confirmación");
			if (selectedTrip == 0)
				return;
		}

		boolean hasUsers = showUsers(selectedTrip);

		if (!hasUsers)
			return;

		long selectedUser = -1L;
		while (!isUserPendings(selectedUser)) {
			selectedUser = Console
					.readLong("Escoja el usuario para el que quiere confirmar"
							+ " su asistencia \nmediante su ID, inserte 0 si quiere detener la confirmación");
			if (selectedUser == 0)
				return;
		}

		Main.client.confirmPassenger(selectedUser, selectedTrip);
		Console.println("Confirmación realizada");

	}

	private boolean isUserPendings(Long idUser) {

		for (User user : usersToConfirm) {
			if (user.getId().equals(idUser))
				return true;
		}
		return false;

	}

	private boolean showUsers(Long idTrip) {

		usersToConfirm = Main.client.getUserInvolvedInTrip(idTrip,
				Main.user.getId());

		if (usersToConfirm.isEmpty()) {
			Console.println("No tiene peticiones para este viaje");
			return false;
		}

		for (User user : usersToConfirm) {
			printUser(user);
		}

		return true;
	}

	private void printUser(User user) {

		Console.println("ID:" + user.getId() + " " + user.getName() + ", "
				+ user.getSurname() + "  email: " + user.getEmail());
	}

	private boolean isTrip(Long id) {

		if (Main.trips == null) {
			Main.trips = Main.client.getTrips(Main.user.getId());
		}

		for (Trip trip : Main.trips) {
			if (trip.getId().equals(id))
				return true;
		}
		return false;
	}

}
