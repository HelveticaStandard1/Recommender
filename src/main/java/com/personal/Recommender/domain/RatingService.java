package com.personal.Recommender.domain;

import java.util.List;

import com.personal.Recommender.entity.Opinion;
import com.personal.Recommender.entity.Rating;

public interface RatingService {

	/**
	 * Get All Ratings without any filtering
	 * @return
	 */
	public List<Rating> getRatings();

	/**
	 * Retrieve list of ratings based on restaurant id
	 * @param restaurantId
	 * @return
	 */
	public List<Rating> getRatings(String restaurantId);

	/**
	 * Given an opinion LIKE/DISLIKE, get all the ratings on a restaurant that match
	 * the opinion.
	 * 
	 * @param ratingsOnRestaurant
	 * @param opinion
	 * @return
	 */
	public List<Rating> getRatings(String restaurantId, Opinion opinion);

}
