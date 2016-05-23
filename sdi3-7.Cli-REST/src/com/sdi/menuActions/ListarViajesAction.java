package com.sdi.menuActions;

import com.sdi.Main;
import com.sdi.model.Trip;

import alb.util.console.Console;
import alb.util.menu.Action;

public class ListarViajesAction implements Action {

	@Override
	public void execute() throws Exception {

		Console.println("Estos son los viajes que usted esta "
				+ "ofertando en este momento");
		if (Main.trips == null) {
			Main.trips = Main.client.getTrips(Main.user.getId());
		}
		for (Trip trip : Main.trips) {
			printTrip(trip);
		}

	}

	private void printTrip(Trip trip) {

		Console.println("ID: " + trip.getId() + " "
				+ trip.getDestination().getCity() + " ("
				+ trip.getDeparture().getCountry() + ") - "
				+ trip.getDestination().getCity() + " ("
				+ trip.getDestination().getCountry() + ")" +  "hora salida: "
				+ trip.getDepartureDate());

	}

}
