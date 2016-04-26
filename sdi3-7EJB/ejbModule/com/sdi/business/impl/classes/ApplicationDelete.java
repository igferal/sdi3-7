package com.sdi.business.impl.classes;

import alb.util.log.Log;

import com.sdi.infrastructure.Factories;

public class ApplicationDelete {

	public void delete(Long idUser, Long idTrip) {
		Factories.persistence.newApplicationDao().delete(new Long[]{idUser, idTrip});
		Log.info("Application eliminada para el usuario [%s] en el viaje [%s]", idUser, idTrip);
	}

}
