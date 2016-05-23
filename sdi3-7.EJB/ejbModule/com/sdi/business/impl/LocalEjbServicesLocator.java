package com.sdi.business.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.sdi.business.ApplicationService;
import com.sdi.business.RatingService;
import com.sdi.business.SeatService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.TripService;
import com.sdi.business.UserService;

public class LocalEjbServicesLocator implements ServicesFactory {

	private static final String APPLICATION_SERVICE_JNDI_KEY =
			"java:global/"
			+ "sdi3-7/"
			+ "sdi3-7.EJB/"
			+ "EjbApplicationService!"
			+ "com.sdi.business.impl.LocalApplicationService";
	
	private static final String RATING_SERVICE_JNDI_KEY =
			"java:global/"
			+ "sdi3-7/"
			+ "sdi3-7.EJB/"
			+ "EjbRatingService!"
			+ "com.sdi.business.impl.LocalRatingService";
	
	private static final String SEAT_SERVICE_JNDI_KEY =
			"java:global/"
			+ "sdi3-7/"
			+ "sdi3-7.EJB/"
			+ "EjbSeatService!"
			+ "com.sdi.business.impl.LocalSeatService";
	
	private static final String TRIP_SERVICE_JNDI_KEY =
			"java:global/"
			+ "sdi3-7/"
			+ "sdi3-7.EJB/"
			+ "EjbTripService!"
			+ "com.sdi.business.impl.LocalTripService";
	
	private static final String USER_SERVICE_JNDI_KEY =
			"java:global/"
			+ "sdi3-7/"
			+ "sdi3-7.EJB/"
			+ "EjbUserService!"
			+ "com.sdi.business.impl.LocalUserService";

	@Override
	public ApplicationService getApplicationService() {
		try {
			Context ctx = new InitialContext();
			return (ApplicationService) ctx.lookup(APPLICATION_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}
	
	@Override
	public RatingService getRatingService() {
		try {
			Context ctx = new InitialContext();
			return (RatingService) ctx.lookup(RATING_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

	@Override
	public SeatService getSeatService() {
		try {
			Context ctx = new InitialContext();
			return (SeatService) ctx.lookup(SEAT_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}
	
	@Override
	public TripService getTripService() {
		try {
			Context ctx = new InitialContext();
			return (TripService) ctx.lookup(TRIP_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}
	
	@Override
	public UserService getUserService() {
		try {
			Context ctx = new InitialContext();
			return (UserService) ctx.lookup(USER_SERVICE_JNDI_KEY);
		} catch (NamingException e) {
			throw new RuntimeException("JNDI problem", e);
		}
	}

}
