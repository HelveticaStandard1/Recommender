package com.personal.Recommender.domain;

import java.util.List;

import com.personal.Recommender.entity.Rating;
import com.personal.Recommender.entity.Restaurant;
import com.personal.Recommender.entity.Teammate;

public interface FunctionalService {

	/**
	 * Given a restaurant and a teammate, return whether or the teammate has ever
	 * rated this restaurant
	 * 
	 * @param requested
	 * @param restaurant
	 * @return
	 */
	public boolean teammateHasRated(Teammate teammate, Restaurant restaurant);

	/**
	 * For each rating given, calculate the similarity index between the requested
	 * teammate and each other teammate that has rated the restaurant.
	 * 
	 * @param opinions
	 * @param requested
	 * @return
	 */
	public double getSimilaritySum(List<Rating> opinions, Teammate teammate);

	/**
	 * Calculate a restaurants recommended rating for a given teammate
	 * 
	 * @param restaurant
	 * @param teammate
	 * @return
	 */
	public double calcRestaurantRatings(Restaurant restaurant, Teammate teammate);

}
