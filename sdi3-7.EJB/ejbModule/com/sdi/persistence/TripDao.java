package com.sdi.persistence;

import java.util.Date;
import java.util.List;

import com.sdi.model.Trip;
import com.sdi.persistence.util.GenericDao;



public interface TripDao extends GenericDao<Trip, Long> {

	Trip findByPromoterIdAndArrivalDate(Long id, Date arrivalDate);

	List<Trip> findTravelsAfter(Date date);

	List<Trip> dateAscending(Date date);

	List<Trip> dateDescending(Date date);

	List<Trip> destinoDescending(Date date);

	List<Trip> destinoAscending(Date date);
	
	
	List<Trip> dateAscendingPromoter(Long idProm);

	List<Trip> dateDescendingPromoter(Long idProm);

	List<Trip> destinoDescendingPromoter(Long idProm);

	List<Trip> destinoAscendingPromoter(Long idProm);

	List<Trip> findTravelsOpenAndClosed();
	
	List<Trip> findTripsByUserIdNotPromoter(Long id);

}
