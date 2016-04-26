package com.sdi.business;

public interface ServicesFactory {
	
	UserService createUserService();
	TripService createTripService();
	ApplicationService createApplicationService();
	SeatService createSeatService();
}
