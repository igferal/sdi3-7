package com.sdi.rest;

import java.util.Date;
import java.util.List;

import com.sdi.business.TripService;
import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.infrastructure.Factories;
import com.sdi.model.Trip;
import com.sdi.model.User;

public class TripServiceRestImpl implements TripServiceRest {

	@Override
	public List<Trip> getTrips(Long id) {

		TripService tservice = Factories.services.getTripService();

		return tservice.currentTravelsPromoter(310L, new Date());
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

	@Override
	public User login(String name, String password)
			throws EntityNotFoundException {
		
		User user = Factories.services.getUserService().verify(name, password);
		return user;
	}
}
