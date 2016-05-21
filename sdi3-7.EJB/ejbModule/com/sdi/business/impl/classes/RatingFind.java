package com.sdi.business.impl.classes;

import java.util.List;

import com.sdi.infrastructure.Factories;
import com.sdi.model.Rating;

public class RatingFind {

	public List<Rating> findLastMonthDone() {
		return Factories.persistence.newRatingDao().findLastMonthDone();
	}

	public List<Rating> findAll() {
		return Factories.persistence.newRatingDao().findAll();
	}

}
