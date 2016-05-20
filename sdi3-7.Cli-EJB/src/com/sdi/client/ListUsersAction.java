package com.sdi.client;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

import alb.util.menu.Action;

public class ListUsersAction implements Action {

	@Override
	public void execute() throws Exception {
		List<User> users = Factories.services.getUserService().findAll();
		System.out.println(users.size());
	}

}
