package com.sdi.business;

import java.util.Date;
import java.util.List;

import com.sdi.model.Trip;

public interface TripService {
	
	Trip findTrip(Long id);
	void saveTrip(Trip trip);
	List<Trip> listActiveTrips(Date date);
	List<Trip> travelsPromoter(Long id);
	void update(Trip trip);
	void updateTripsStatusTask();
}
