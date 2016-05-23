package com.sdi.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sdi.business.exception.EntityNotFoundException;
import com.sdi.model.Trip;
import com.sdi.model.User;

@Path("/TripServiceRs")
public interface TripServiceRest {
	
	@GET
	@Path("/login{name}/{password}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	User login(@PathParam(value = "name") String name,@PathParam(value = "password") String password ) throws EntityNotFoundException;
	

	@GET
	@Path("/getMyTrips{idUser}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	List<Trip> getTrips(@PathParam(value = "idUser") Long idTrip);
	
	@GET
	@Path("/involvedUsers{idTrip}/id{promoter}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	List<User> getUserInvolvedInTrip(@PathParam(value = "idTrip") Long idTrip,@PathParam(value = "promoter") Long idPromoter)
			throws EntityNotFoundException;

	
	
	@PUT
	@Path("/confirmUser{idUser}/InTrip{idTrip}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	void confirmPassenger(@PathParam(value = "idUser") Long idUser, 
		@PathParam(value = "idTrip") Long idTrip) throws EntityNotFoundException;
	
	@GET
	@Path("/travelsPromoter{idPromoter}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	List<Trip> getPromoterTrips(@PathParam(value="idPromoter") Long idPromoter);
	
	@GET
	@Path("/travelsAccepted{idPromoter}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	List<Trip> getTripsAccepted(@PathParam(value="idPromoter") Long idPromoter);
	
	@GET
	@Path("/findTrip{idTrip}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Trip findTrip(@PathParam(value="findTrip") Long idTrip);
;

}
