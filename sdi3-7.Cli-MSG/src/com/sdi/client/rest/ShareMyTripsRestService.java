package com.sdi.client.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sdi.client.model.User;
import com.sdi.client.model.Trip;

@Path("/TripServiceRs")
public interface ShareMyTripsRestService {

	@GET
	@Path("/login{name}/{password}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	User login(@PathParam(value = "name") String name,
			@PathParam(value = "password") String password);

	@GET
	@Path("/travelsPromoter{idPromoter}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	List<Trip> getPromoterTrips(@PathParam(value = "idPromoter") Long idPromoter);

	@GET
	@Path("/travelsAccepted{idPromoter}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	List<Trip> getTripsAccepted(@PathParam(value = "idPromoter") Long idPromoter);

	@GET
	@Path("/findTrip{idTrip}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	Trip findTrip(@PathParam(value = "idTrip") Long idTrip);

}
