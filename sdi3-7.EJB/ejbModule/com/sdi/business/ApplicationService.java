package com.sdi.business;

import com.sdi.model.Application;

public interface ApplicationService {

	public boolean save(Application application);
	public void delete(Long idUser, Long idTrip);
}
