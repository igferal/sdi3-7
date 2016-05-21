package client.menuActions;

import java.util.List;

import client.Main;
import client.model.Trip;
import client.model.User;
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
							+ " usuarios \nmediante su ID");
		}
		showUsers(selectedTrip);

		long selectedUser = -1L;
		while (!isUserPendings(selectedUser)) {
			selectedUser = Console
					.readLong("Escoja el usuario para el que quiere confirmar"
							+ " su asistencia \nmediante su ID");
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

	private void showUsers(Long idTrip) {

		usersToConfirm = Main.client.getUserInvolvedInTrip(idTrip,
				Main.user.getId());

		for (User user : usersToConfirm) {
			System.out.println(user.toString());
		}

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
