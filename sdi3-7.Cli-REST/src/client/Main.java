package client;


import java.util.List;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import client.menuActions.ConfirmPassengerAction;
import client.menuActions.ListarViajesAction;
import client.model.Trip;
import client.model.User;
import alb.util.console.Console;
import alb.util.menu.BaseMenu;

public class Main extends BaseMenu {

	private static final String REST_SERVICE_URL = "http://localhost:8280/sdi3-7.Web/rest/";
	public static ShareMyTripsRestService client;
	public static User user;
	public static List<Trip> trips;

	public static void main(String[] args) {
		new Main().execute();
	}

	private  Main() {
		client = new ResteasyClientBuilder().build()
				.register(new Authenticator("sdi", "password"))
				.target(REST_SERVICE_URL).proxy(ShareMyTripsRestService.class);

		while (login() == false)
			;

		menuOptions = new Object[][] { { "Listar viajes ofrecidos en vigor",
				ListarViajesAction.class }, { "Confirmar usuarios en un viaje",
ConfirmPassengerAction.class }

		};

	}

	private boolean login() {
		String name = Console.readString("Inserte el usuario");
		String password = Console.readString("Inserte la contraseña");
		user = client.login(name, password);
		if (user == null) {
			Console.println("Usuario invalido, introduzca uno correcto");
			return false;
		}
		return true;
	}


}
