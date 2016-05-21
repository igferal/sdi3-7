package com.sdi.business;

public interface ServicesFactory {
	
	ApplicationService getApplicationService();
	RatingService getRatingService();
	SeatService getSeatService();
	TripService getTripService();
	UserService getUserService();
	
}
