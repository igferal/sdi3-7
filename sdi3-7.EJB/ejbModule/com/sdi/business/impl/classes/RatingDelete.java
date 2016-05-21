package com.sdi.business.impl.classes;

import com.sdi.infrastructure.Factories;

public class RatingDelete {

	public int delete(Long id) {
		return Factories.persistence.newRatingDao().delete(id);
	}

}
