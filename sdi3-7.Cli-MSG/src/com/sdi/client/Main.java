package com.sdi.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;

import alb.util.console.Console;

import com.sdi.business.TripService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.model.User;

public class Main {

	private static final String JMS_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	private static final String SDI3_7_TOPIC = "jms/topic/topicsdi3-7";

	private User user;
	private Long idTrip;

	public static void main(String[] args) throws JMSException {
		new Main().run();
	}

	private void run() throws JMSException {
		login();
		listTrips();
		selectTrip();
	}

	private void login() {
		String userName = Console.readString("Usuario");
		String password = Console.readString("Password");

		user = Factories.services.getUserService().verify(userName, password);

		while (user == null) {
			Console.println("Usuario o password incorrectos");
			userName = Console.readString("Usuario");
			password = Console.readString("Password");
			user = Factories.services.getUserService().verify(userName,
					password);
		}
	}

	private void listTrips() {
		TripService tService = Factories.services.getTripService();
		List<Trip> promoterTrips = tService.travelsPromoter(user.getId());
		List<Trip> acceptedTrips = tService.tripsAccepted(user.getId());

		promoterTrips.addAll(acceptedTrips);

		System.out.printf("%s %s %s %s\n", "__ID__",
				"_SALIDA_____________________________________________________",
				"_LLEGADA____________________________________________________",
				"_PLAZAS_LIBRES_");

		for (Trip t : promoterTrips) {
			System.out.printf(
					"%-6s %-60s %-60s %-15s\n",
					t.getId(),
					t.getDeparture().escapeToConsole() + " "
					+ formatDate(t.getDepartureDate()),
					t.getDestination().escapeToConsole() + " "
					+ formatDate(t.getArrivalDate()),
					t.getAvailablePax() + "/" + t.getMaxPax());
		}
	}

	private void selectTrip() {
		idTrip = Console.readLong("IDTRIP a seleccionar");

		Trip trip = Factories.services.getTripService().findTrip(idTrip);

		while (trip == null) {
			Console.println("El viaje no existe...");
			idTrip = Console.readLong("IDTRIP a seleccionar");
			trip = Factories.services.getTripService().findTrip(idTrip);
		}
	}

	private String formatDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
	}
}
