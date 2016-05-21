package com.sdi.business.impl.classes;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

public class UserUpdate {

	public void update(User user) {
		Factories.persistence.newUserDao().update(user);
	}
	
}
