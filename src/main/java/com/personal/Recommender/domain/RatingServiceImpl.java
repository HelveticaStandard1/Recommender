package com.personal.Recommender.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.Recommender.dao.DataRetrieval;
import com.personal.Recommender.entity.Opinion;
import com.personal.Recommender.entity.Rating;

@Service
public class RatingServiceImpl implements RatingService {

	@Autowired
	DataRetrieval dataRetrieval;

	@Override
	public List<Rating> getRatings() {
		return dataRetrieval.getRatings();
	}

	@Override
	public List<Rating> getRatings(String restaurantId) {
		return getRatings().stream().filter(rating -> rating.getRestaurantId().equals(restaurantId))
				.collect(Collectors.toList());
	}

	@Override
	public List<Rating> getRatings(String restaurantId, Opinion opinion) {
		return getRatings(restaurantId).stream().filter(rating -> rating.getRating().equals(opinion.toString()))
				.collect(Collectors.toList());
	}

}
