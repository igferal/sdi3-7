package com.sdi.client;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import com.sdi.client.model.User;



public class Subscriber implements MessageListener {

	private TopicSession session;
	private TopicSubscriber subscriber;
	
	private User user;
	private Long idTrip;
	
	public Subscriber(TopicConnection connection, Topic topic, User user, Long idTrip) throws Exception {
		this.session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		this.subscriber = this.session.createSubscriber(topic, null, false);
		this.subscriber.setMessageListener(this);
		this.user = user;
		this.idTrip = idTrip;
	}
	
	@Override
	public void onMessage(Message m) {
		MapMessage msg = (MapMessage) m;
		try {
			//Lanza NumberFormatException si el user no esta en el viaje
			msg.getLong(user.getId().toString());
			if (msg.getLong("idTrip") == idTrip)
				System.out.println(msg.getString("login") + " dice: " + msg.getString("message"));
		} catch (NumberFormatException e) {
			//El user no esta en el viaje...
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() throws Exception  {
		subscriber.close();
		session.close();
	}

}
