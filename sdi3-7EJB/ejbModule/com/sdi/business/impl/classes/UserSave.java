package com.sdi.business.impl.classes;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;
import com.sdi.model.User;
import com.sdi.persistence.UserDao;

public class UserSave {

	public boolean save(User user) {
		UserDao dao = Factories.persistence.newUserDao();

		if (dao.findByLogin(user.getLogin()) != null)
			return false;

		dao.save(user);
		Log.info("Usuario [%s] con login [%s] creado", user.getId(),
				user.getLogin());

		return true;
	}

}
