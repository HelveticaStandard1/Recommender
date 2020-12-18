package com.personal.Recommender.utility;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.personal.Recommender.entity.Rating;

class UtilsTest {

	String restaurantIdOne = "restaurantIdOne";
	String restaurantIdTwo = "restaurantIdTwo";
	String restaurantIdThree = "restaurantIdThree";

	@Test
	void testCalcSim() {
		int unionLL = 1;
		int unionDD = 2;
		int unionLD = 1;
		int unionDL = 2;
		int totalOne = 3;
		int totalTwo = 3;
		assertEquals(0, Utils.calcSim(unionLL, unionDD, unionLD, unionDL, totalOne, totalTwo));
	}

	@Test
	void testGetUnionRating() {
		List<Rating> ratingsOne = new ArrayList<>();
		List<Rating> ratingsTwo = new ArrayList<>();
		
		Rating ratingOne = getRating(restaurantIdOne);
		Rating ratingTwo = getRating(restaurantIdTwo);
		Rating ratingThree = getRating(restaurantIdThree);
		
		ratingsOne.add(ratingOne);
		ratingsOne.add(ratingTwo);
		
		ratingsTwo.add(ratingTwo);
		ratingsTwo.add(ratingThree);
		
		List<Rating> ratings = Utils.getUnionRating(ratingsOne, ratingsTwo);
		
		assertEquals(1, ratings.size());
		assertEquals(ratingTwo, ratings.get(0));
		
	}

	private Rating getRating(String restaurantId) {
		Rating rating = new Rating();
		rating.setRestaurantId(restaurantId);
		return rating;
	}

}
