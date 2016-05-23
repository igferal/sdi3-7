package com.sdi.client;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import alb.util.console.Console;

import com.sdi.client.model.Trip;
import com.sdi.client.model.User;
import com.sdi.client.rest.Authenticator;
import com.sdi.client.rest.ShareMyTripsRestService;
import com.sdi.client.util.Jndi;

public class Main {

	private static final String JMS_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
	private static final String SDI3_7_TOPIC = "jms/topic/topicsdi3-7";
	private static final String SDI3_7_QUEUE = "jms/queue/queuesdi3-7";
	private static final String REST_SERVICE_URL = "http://localhost:8280/sdi3-7.Web/sdi3-7Rest/";

	private User user;
	private Long idTrip;
	private ShareMyTripsRestService client;

	public static void main(String[] args) throws Exception {
		new Main().run();
	}

	private void run() throws Exception {
		client = new ResteasyClientBuilder().build()
				.register(new Authenticator("sdi", "password"))
				.target(REST_SERVICE_URL).proxy(ShareMyTripsRestService.class);

		login();
		listTrips();
		selectTrip();

		TopicConnectionFactory factory = (TopicConnectionFactory) Jndi
				.find(JMS_CONNECTION_FACTORY);

		Topic topic = (Topic) Jndi.find(SDI3_7_TOPIC);
		TopicConnection con = factory.createTopicConnection("sdi", "password");

		Subscriber subs = new Subscriber(con, topic, user, idTrip);

		con.start();

		Console.println("Bienvenido al chat del viaje " + idTrip);
		Console.println("Conectado como " + user.getLogin() + "\n");
		Console.println("Escriba y presione enter para enviar sus mensajes");
		
		ask();

		subs.close();
		con.close();
	}

	private void ask() throws JMSException {
		while (true) {
			String msg = Console.readString();
			sendMessage(msg);
		}
	}

	private void sendMessage(String msg) throws JMSException {
		ConnectionFactory factory = (ConnectionFactory) Jndi
				.find(JMS_CONNECTION_FACTORY);
		Queue queue = (Queue) Jndi.find(SDI3_7_QUEUE);
		Session session = factory.createConnection("sdi", "password")
				.createSession(false, Session.AUTO_ACKNOWLEDGE);
		MessageProducer sender = session.createProducer(queue);

		sender.send(createMessage(msg, session));
	}

	private MapMessage createMessage(String message, Session session)
			throws JMSException {
		MapMessage msg = session.createMapMessage();
		msg.setLong("idTrip", idTrip);
		msg.setLong("idUser", user.getId());
		msg.setString("login", user.getLogin());
		msg.setString("message", message);

		return msg;
	}

	private void login() {
		String userName = Console.readString("Usuario");
		String password = Console.readString("Password");

		user = client.login(userName, password);

		while (user == null) {
			Console.println("Usuario o password incorrectos");
			userName = Console.readString("Usuario");
			password = Console.readString("Password");
			user = client.login(userName, password);
		}
	}

	private void listTrips() {
		List<Trip> promoterTrips = client.getPromoterTrips(user.getId());
		List<Trip> acceptedTrips = client.getTripsAccepted(user.getId());

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
							+ formatDate(t.getDepartureDate()), t
							.getDestination().escapeToConsole()
							+ " "
							+ formatDate(t.getArrivalDate()),
					t.getAvailablePax() + "/" + t.getMaxPax());
		}
	}

	private void selectTrip() {
		idTrip = Console.readLong("\nIDTRIP a seleccionar");

		Trip trip = client.findTrip(idTrip);

		while (trip == null) {
			Console.println("El viaje no existe...");
			idTrip = Console.readLong("IDTRIP a seleccionar");
			trip = client.findTrip(idTrip);
		}

	}

	private String formatDate(Date date) {
		return new SimpleDateFormat("dd/MM/yyyy HH:mm").format(date);
	}
}
