package com.sdi.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

import alb.util.console.Console;

import com.sdi.business.TripService;
import com.sdi.client.util.Jndi;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.model.User;

public class Main {

	private static final String JMS_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	private static final String SDI3_7_TOPIC = "jms/topic/topicsdi3-7";

	private User user;
	private Long idTrip;
	private PublisherAndSubscriber ps;

	public static void main(String[] args) throws Exception {
		new Main().run();
	}

	private void run() throws Exception {
		login();
		listTrips();
		selectTrip();
		
		TopicConnectionFactory factory =
				(TopicConnectionFactory) Jndi.find( JMS_CONNECTION_FACTORY );
		
		Topic topic = (Topic) Jndi.find(SDI3_7_TOPIC);
		TopicConnection con = factory.createTopicConnection("sdi", "password");
		
//		List<Seat> seats = Factories.services.getSeatService().findAcceptedByTrip(id);
		
		ps = new PublisherAndSubscriber(con, topic, user);
		
		con.start();
		
		ask();
	
		
	}

	private void ask() throws JMSException {
		while (true) {
			String msg = Console.readString("Mensaje a enviar");
			ps.sendMessage(msg);
		}
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
