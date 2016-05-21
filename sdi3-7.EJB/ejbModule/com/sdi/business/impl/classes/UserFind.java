package com.sdi.business.impl.classes;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;

public class UserFind {

	public User find(String login) {
		return Factories.persistence.newUserDao().findByLogin(login);
	}

	public List<User> findAll() {
		return Factories.persistence.newUserDao().findAll();
	}

	public User findById(Long id) {
		return Factories.persistence.newUserDao().findById(id);
	}
	
}
