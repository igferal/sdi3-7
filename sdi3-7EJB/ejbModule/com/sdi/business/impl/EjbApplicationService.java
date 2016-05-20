package com.sdi.business.impl;

import javax.ejb.Stateless;

import com.sdi.business.impl.classes.ApplicationDelete;
import com.sdi.business.impl.classes.ApplicationSave;
import com.sdi.model.Application;

@Stateless
public class EjbApplicationService implements LocalApplicationService, RemoteApplicationService {

	@Override
	public boolean save(Application application) {
		return new ApplicationSave().save(application);
	}

	@Override
	public void delete(Long idUser, Long idTrip) {
		new ApplicationDelete().delete(idUser, idTrip);
	}

}
