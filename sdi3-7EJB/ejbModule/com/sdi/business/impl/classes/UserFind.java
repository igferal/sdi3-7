package com.sdi.business.impl.classes;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

public class UserFind {

	public User find(String login) {
		return Factories.persistence.newUserDao().findByLogin(login);
	}
	
}
