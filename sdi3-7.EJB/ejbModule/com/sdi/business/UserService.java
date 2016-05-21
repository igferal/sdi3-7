package com.sdi.business;

import java.util.List;

import com.sdi.model.User;

public interface UserService {

	public User verify(String user, String password);
	public boolean saveUser(User user);
	public List<User> findAll();
	public User findById(Long id);
	public void updateUser(User user);
}
