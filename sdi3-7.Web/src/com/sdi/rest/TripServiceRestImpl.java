package com.sdi.rest;

import java.util.Date;
import java.util.List;

import com.sdi.business.TripService;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;

public class TripServiceRestImpl implements TripServiceRest {

	
	@Override
	public List<Trip> getTrips() {
		
		TripService tservice = Factories.services.getTripService();

		
		return tservice.listActiveTrips(new Date());
	}

	@Override
	public List<Trip> getUserInvolvedInTrip(Long idTrip)
			throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean confirmPassenger(Long idUser, Long idTrip)
			throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}



}
