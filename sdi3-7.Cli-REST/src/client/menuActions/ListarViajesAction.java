package client.menuActions;

import client.Main;
import client.model.Trip;
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
			Console.println(trip);
		}

	}

}