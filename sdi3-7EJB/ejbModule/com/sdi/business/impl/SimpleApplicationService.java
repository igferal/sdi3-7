package com.sdi.business.impl;

import com.sdi.business.ApplicationService;
import com.sdi.business.impl.classes.ApplicationDelete;
import com.sdi.business.impl.classes.ApplicationSave;
import com.sdi.model.Application;

public class SimpleApplicationService implements ApplicationService {

	@Override
	public boolean save(Application application) {
		return new ApplicationSave().save(application);
	}

	@Override
	public void delete(Long idUser, Long idTrip) {
		new ApplicationDelete().delete(idUser, idTrip);
	}

}
