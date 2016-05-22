package com.sdi.client;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;

import com.sdi.model.User;

public class PublisherAndSubscriber implements MessageListener {

	private TopicSession session;
	private TopicPublisher publisher;
	private TopicSubscriber subscriber;
	
	private User user;
	
	public PublisherAndSubscriber(TopicConnection connection, Topic topic, User user) throws Exception {
		this.session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		this.publisher = this.session.createPublisher(topic);
		this.subscriber = this.session.createSubscriber(topic, null, false);
		this.subscriber.setMessageListener(this);
		this.user = user;
	}
	
	public void sendMessage(String message) throws JMSException {
		Message m = createMessage(message);
		publisher.send(m);
	}
	
	private MapMessage createMessage(String message) throws JMSException {
		MapMessage msg = session.createMapMessage();
		msg.setLong("iduser", user.getId());
		msg.setString("login", user.getLogin());
		msg.setString("message", message);
		
		return msg;
	}
	
	@Override
	public void onMessage(Message m) {
		MapMessage msg = (MapMessage) m;
		try {
			System.out.println(msg.getString("login") + " dice: " + msg.getString("message"));
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void close() throws Exception  {
		publisher.close();
		subscriber.close();
		session.close();
	}

}
