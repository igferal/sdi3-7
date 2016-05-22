package com.sdi.client;

import java.util.List;

import com.sdi.ws.EjbTripServiceService;
import com.sdi.ws.EjbUserServiceService;
import com.sdi.ws.TripService;
import com.sdi.ws.User;
import com.sdi.ws.UserService;

import alb.util.menu.Action;

public class ListUsersAction implements Action {

	@Override
	public void execute() throws Exception {
		UserService userService = new EjbUserServiceService().getUserServicePort();
		List<User> users = userService.findAllUsers();
		System.out.printf("%s %s %s %s %s %s %s %s\n", "_IDUSER_",
				"_LOGIN_________", "_NOMBRE________",
				"_APELLIDOS_______________", "_EMAIL___________________",
				"_ESTADO________", "_NUM VIAJES PROMOVIDOS___",
				"_NUM VIAJES PARTICIPANDO_");

		int promotedTravels = 0;
		int participatedTravels = 0;

		TripService tService = new EjbTripServiceService().getTripServicePort();

		for (User user : users) {
			promotedTravels = tService.travelsPromoter(user.getId()).size();
			participatedTravels = tService.tripsTakePartOf(user.getId()).size();
			System.out.printf(
					"%-8s %-15s %-15s %-25s %-25s %-15s %-25s %-25s\n",
					user.getId(), user.getLogin(), user.getName(),
					user.getSurname(), user.getEmail(), user.getStatus(),
					promotedTravels, participatedTravels);
		}

	}

}
