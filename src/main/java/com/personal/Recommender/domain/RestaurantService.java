package com.personal.Recommender.domain;

import java.util.List;

import com.personal.Recommender.entity.Restaurant;

public interface RestaurantService {

	/**
	 * Get List of Restaurants without filtering
	 * @return
	 */
	public List<Restaurant> getRestaurants();

	/**
	 * Get Specific Restaurant based on ID
	 * @param id
	 * @return
	 */
	public Restaurant getRestaurant(String id);
	
}
