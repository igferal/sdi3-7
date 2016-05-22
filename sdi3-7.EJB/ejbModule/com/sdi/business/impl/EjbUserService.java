package com.sdi.business.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;

import com.sdi.business.impl.classes.UserFind;
import com.sdi.business.impl.classes.UserSave;
import com.sdi.business.impl.classes.UserUpdate;
import com.sdi.model.User;

@Stateless
@WebService(name = "UserService")
public class EjbUserService implements LocalUserService, RemoteUserService {

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

	@Override
	public List<User> findAllUsers() {
		return new UserFind().findAll();
	}

	@Override
	public User findById(Long id) {
		return new UserFind().findById(id);
	}

	@Override
	public void updateUser(User user) {
		new UserUpdate().update(user);
	}

}
