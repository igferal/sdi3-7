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

		return tservice.currentTravelsPromoter(id, new Date());
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



	@Override
	public List<User> getUserInvolvedInTrip(Long idTrip, Long idPromoter)
			throws EntityNotFoundException {
		return Factories.services.getTripService().pendingUsers(idTrip, idPromoter);
	}
}
