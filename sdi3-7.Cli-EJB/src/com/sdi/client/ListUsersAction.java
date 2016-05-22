package com.sdi.client;

import java.util.List;

import com.sdi.business.ServicesFactory;
import com.sdi.business.TripService;
import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

import alb.util.menu.Action;

public class ListUsersAction implements Action {

	@Override
	public void execute() throws Exception {
		ServicesFactory services = Factories.services;
		List<User> users = services.getUserService().findAllUsers();
		System.out.printf("%s %s %s %s %s %s %s %s\n",
				"_IDUSER_",
				"_LOGIN_________",
				"_NOMBRE________",
				"_APELLIDOS_______________",
				"_EMAIL___________________",
				"_ESTADO________",
				"_NUM_VIAJES_PROMOVIDOS___",
				"_NUM_VIAJES_HA_PARTICIPADO_");
		
		int promotedTravels = 0;
		int participatedTravels = 0;
		
		TripService tService = services.getTripService();
		
		for (User user:users) {
			promotedTravels = tService.travelsPromoter(user.getId()).size();
			participatedTravels = tService.tripsTakePartOf(user.getId()).size();
			System.out.printf("%-8s %-15s %-15s %-25s %-25s %-15s %-25s %-25s\n",
					user.getId(),
					user.getLogin(),
					user.getName(),
					user.getSurname(),
					user.getEmail(),
					user.getStatus(),
					promotedTravels,
					participatedTravels);
		}
		
	}

}
