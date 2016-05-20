package com.sdi.business.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import com.sdi.business.impl.classes.ListActiveTrip;
import com.sdi.business.impl.classes.ListPromoterTrips;
import com.sdi.business.impl.classes.TripFind;
import com.sdi.business.impl.classes.TripSave;
import com.sdi.business.impl.classes.TripUpdate;
import com.sdi.business.impl.classes.TripsUpdateStatusTask;
import com.sdi.model.Trip;

@Stateless
public class EjbTripService implements LocalTripService, RemoteTripService {

	@Override
	public void saveTrip(Trip trip) {
		new TripSave(trip).save();
	}

	@Override
	public List<Trip> listActiveTrips(Date date) {
		return new ListActiveTrip(date).listActiveTrips();
	}

	public List<Trip> travelsPromoter(Long id) {
		return new ListPromoterTrips(id).listTrips();
	}

	@Override
	public void update(Trip trip) {
		 new TripUpdate(trip).update();
	}

	@Override
	public Trip findTrip(Long id) {
		return new TripFind().findById(id);
	}

	@Override
	public void updateTripsStatusTask() {
		new TripsUpdateStatusTask();
	}
	
}
