package com.sdi.business;

public interface ServicesFactory {
	
	ApplicationService getApplicationService();
	SeatService getSeatService();
	TripService getTripService();
	UserService getUserService();
	
}
