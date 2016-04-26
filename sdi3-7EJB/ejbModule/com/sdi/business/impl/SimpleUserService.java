package com.sdi.business.impl;

import com.sdi.business.UserService;
import com.sdi.business.impl.classes.UserFind;
import com.sdi.business.impl.classes.UserSave;
import com.sdi.model.User;

public class SimpleUserService implements UserService {

	@Override
	public User verify(String login, String password) {
		User user = new UserFind().find(login);
		if (user != null && user.getPassword().equals(password)) {
			user.setPassword(null);
			return user;
		}
		
		return null;
	}

	@Override
	public boolean saveUser(User user) {
		return new UserSave().save(user);
	}

}
