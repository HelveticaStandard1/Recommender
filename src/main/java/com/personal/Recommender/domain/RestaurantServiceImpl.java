package com.personal.Recommender.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.Recommender.dao.DataRetrieval;
import com.personal.Recommender.entity.Restaurant;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	DataRetrieval dataRetrieval;

	@Override
	public List<Restaurant> getRestaurants() {
		return dataRetrieval.getRestaurtants();
	}

	@Override
	public Restaurant getRestaurant(String id) {
		try {
		return getRestaurants().stream().filter(restaurant -> restaurant.getId().equalsIgnoreCase(id)).limit(1)
		.collect(Collectors.toList()).get(0);
		} catch (IndexOutOfBoundsException ex) {
			throw new RuntimeException("Could not find restaurant based on id : " + id);
		}
	}

}
