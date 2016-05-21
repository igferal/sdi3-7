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

@Path("/TripServiceRs")
public interface TripServiceRest {

	@GET
	@Path("/getMyTrips")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	List<Trip> getTrips() throws EntityNotFoundException;

	@GET
	@Path("/involvedUsers{idTrip}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	List<Trip> getUserInvolvedInTrip(@PathParam(value = "idTrip") Long idTrip)
			throws EntityNotFoundException;

	
	
	@PUT
	@Path("/confirmUser{idUser}InTrip{idTrip}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	boolean confirmPassenger(@PathParam(value = "id") Long idUser, 
		@PathParam(value = "id") Long idTrip) throws EntityNotFoundException;
;

}
