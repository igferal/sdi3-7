package com.sdi.business.impl;


import com.sdi.business.ApplicationService;
import com.sdi.business.SeatService;
import com.sdi.business.ServicesFactory;
import com.sdi.business.TripService;
import com.sdi.business.UserService;

public class SimpleServicesFactory implements ServicesFactory {
	
	@Override
	public UserService createUserService() {
		return new SimpleUserService();
	}

	@Override
	public TripService createTripService() {
		return new SimpleTripService();
	}

	@Override
	public ApplicationService createApplicationService() {
		return new SimpleApplicationService();
	}

	@Override
	public SeatService createSeatService() {
		return new SimpleSeatService();
	}

}
