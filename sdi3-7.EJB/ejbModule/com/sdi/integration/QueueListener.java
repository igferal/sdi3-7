package com.sdi.integration;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;

import alb.util.log.Log;

import com.sdi.business.impl.LocalSeatService;
import com.sdi.business.impl.LocalTripService;
import com.sdi.model.Seat;
import com.sdi.model.Trip;

@MessageDriven(activationConfig = {
		@ActivationConfigProperty(propertyName = "destinationType", 
				propertyValue = "javax.jms.Queue"),
		@ActivationConfigProperty(propertyName = "destination",
				propertyValue = "queue/queuesdi3-7")})
public class QueueListener implements MessageListener {

	@EJB(beanInterface = LocalSeatService.class)
	private LocalSeatService seatservice;
	
	@EJB(beanInterface = LocalTripService.class)
	private LocalTripService tripService;
	
	@Resource(mappedName = "java:/ConnectionFactory")
	private TopicConnectionFactory factory;
	
	private static TopicConnection con;

	@Resource(mappedName = "java:/jms/topic/topicsdi3-7")
	private Topic topic;

	@Override
	public void onMessage(Message m) {
		MapMessage msg = (MapMessage) m;
		
		try {
			Long idTrip = msg.getLong("idTrip");
			Long idUser = msg.getLong("idUser");
			
			Trip trip = tripService.findTrip(idTrip);
			
			if (trip.getPromoterId().equals(idUser)) {
				sendToTopic(m);
				return;
			}
			
			List<Seat> seats = seatservice.findAcceptedByTrip(idTrip);
			
			for (Seat seat:seats) {
				if (seat.getUserId().equals(idUser)) {
					sendToTopic(m);
					return;
				}
			}
			
			Log.info("El usuario [%s] intentó enviar un mensaje en el viaje [%s]", idUser, idTrip);
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void sendToTopic(Message m) {
		try {
			MapMessage mRecived = (MapMessage) m;
			Long idTrip = mRecived.getLong("idTrip");
			
			List<Seat> seats = seatservice.findAcceptedByTrip(idTrip);
			
			if (con == null)
				con = factory.createTopicConnection("sdi", "password");
			
			TopicSession session = con.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			
			MapMessage mToSend = session.createMapMessage();
			
			mToSend.setLong("idUser", mRecived.getLong("idUser"));
			mToSend.setLong("idTrip", mRecived.getLong("idTrip"));
			mToSend.setString("login", mRecived.getString("login"));
			mToSend.setString("message", mRecived.getString("message"));
			
			for (Seat seat:seats) {
				mToSend.setLong(seat.getUserId().toString(), seat.getUserId());
			}
			
			session.createPublisher(topic).send(mToSend);
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
