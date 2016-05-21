package com.sdi.business.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.sdi.business.impl.classes.RatingDelete;
import com.sdi.business.impl.classes.RatingFind;
import com.sdi.model.Rating;

@Stateless
public class EjbRatingService implements LocalRatingService, RemoteRatingService {

	@Override
	public List<Rating> findLastMonthDone() {
		return new RatingFind().findLastMonthDone();
	}

	@Override
	public List<Rating> findAll() {
		return new RatingFind().findAll();
	}

	@Override
	public int delete(Long id) {
		return new RatingDelete().delete(id);
	}

}
