package com.sdi.business;

import java.util.Date;
import java.util.List;

import com.sdi.model.Trip;
import com.sdi.model.User;

public interface TripService {
	
	Trip findTrip(Long id);
	void saveTrip(Trip trip);
	List<Trip> listActiveTrips(Date date);
	List<Trip> travelsPromoter(Long id);
	List<Trip> currentTravelsPromoter(Long id,Date date);
	void update(Trip trip);
	void updateTripsStatusTask();
	List<Trip> tripsTakePartOf(Long id);
	List<User> pendingUsers(Long idpromoter, Long id_trip);
	List<Trip> tripsAccepted(Long id);
}
