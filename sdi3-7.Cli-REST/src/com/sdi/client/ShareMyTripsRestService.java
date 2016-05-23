package com.sdi.client;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.sdi.model.Trip;
import com.sdi.model.User;





@Path("/TripServiceRs")
public interface ShareMyTripsRestService {

	@POST
	@Path("/login{name}/{password}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	User login(@PathParam(value = "name") String name,
			@PathParam(value = "password") String password);

	@GET
	@Path("/getMyTrips{idUser}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	List<Trip> getTrips(@PathParam(value = "idUser") Long idTrip);

	@GET
	@Path("/involvedUsers{idTrip}/id{idPromoter}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	List<User> getUserInvolvedInTrip(@PathParam(value = "idTrip") Long idTrip,
			@PathParam(value = "idPromoter") Long idPromoter);

	@PUT
	@Path("/confirmUser{idUser}/InTrip{idTrip}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	void confirmPassenger(@PathParam(value = "idUser") Long idUser, 
		@PathParam(value = "idTrip") Long idTrip) ;
}
