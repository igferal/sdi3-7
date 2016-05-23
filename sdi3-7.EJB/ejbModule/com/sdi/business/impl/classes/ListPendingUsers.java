package com.sdi.business.impl.classes;

import java.util.ArrayList;
import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Application;
import com.sdi.model.User;

public class ListPendingUsers {

	private Long idPromotor;

	private Long idTrip;

	public ListPendingUsers(Long idPromotor, Long idTrip) {
		this.idPromotor = idPromotor;
		this.idTrip = idTrip;
	}

	public List<User> listPending() {
		List<User> pendientes = new ArrayList<User>();
		List<Application> applications = Factories.persistence
				.newApplicationDao().appNotInSeat(idTrip);

		for (Application application : applications) {

			if (!application.getUserId().equals(idPromotor)) {
				User user = Factories.persistence.newUserDao().findById(
						application.getUserId());
				user.setPassword(null);
				pendientes.add(user);
			}

		}

		return pendientes;

	}

}
