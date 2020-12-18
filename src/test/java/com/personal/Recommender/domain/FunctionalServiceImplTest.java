package com.personal.Recommender.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.personal.Recommender.entity.Opinion;
import com.personal.Recommender.entity.Rating;
import com.personal.Recommender.entity.Restaurant;
import com.personal.Recommender.entity.Teammate;

@ExtendWith(MockitoExtension.class)
class FunctionalServiceImplTest {

	@Mock
	TeammateService teammateService;

	@Mock
	RatingService ratingService;

	@Mock
	RestaurantService restaurantService;

	@InjectMocks
	@Spy
	FunctionalServiceImpl functionalService = new FunctionalServiceImpl();

	String teammateIdOne = "teammateId";
	String teammateIdTwo = "teammateIdTwo";
	String restaurantId = "restaurantId";

	@Test
	void tstCalcRestaurantRatings() {
		double likeSim = 1.0;
		double dislikeSim = 3.0;
		Teammate teammate = new Teammate();
		teammate.setId(teammateIdOne);

		Restaurant restaurant = new Restaurant();
		restaurant.setId(restaurantId);

		List<Rating> likeRatings = new ArrayList<>();
		Rating likeRating = new Rating();
		likeRating.setRestaurantId(restaurantId);
		likeRating.setTeammateId(teammateIdOne);
		likeRatings.add(likeRating);

		List<Rating> dislikeRatings = new ArrayList<>();
		Rating disLikeRating = new Rating();
		disLikeRating.setRestaurantId(restaurantId);
		disLikeRating.setTeammateId(teammateIdOne);
		dislikeRatings.add(disLikeRating);

		Rating disLikeRatingTwo = new Rating();
		disLikeRatingTwo.setRestaurantId(restaurantId);
		disLikeRatingTwo.setTeammateId(teammateIdOne);
		dislikeRatings.add(disLikeRatingTwo);

		Rating disLikeRatingThree = new Rating();
		disLikeRatingThree.setRestaurantId(restaurantId);
		disLikeRatingThree.setTeammateId(teammateIdOne);
		dislikeRatings.add(disLikeRatingThree);

		when(ratingService.getRatings(restaurant.getId(), Opinion.LIKE)).thenReturn(likeRatings);
		doReturn(likeSim).when(functionalService).getSimilaritySum(likeRatings, teammate);

		when(ratingService.getRatings(restaurant.getId(), Opinion.DISLIKE)).thenReturn(dislikeRatings);
		doReturn(dislikeSim).when(functionalService).getSimilaritySum(dislikeRatings, teammate);

		assertEquals(-0.5, functionalService.calcRestaurantRatings(restaurant, teammate));

	}

	@Test
	void tstCalcRestaurantRatingsNoRatings() {
		double likeSim = 0.0;
		double dislikeSim = 0.0;
		Teammate teammate = new Teammate();
		teammate.setId(teammateIdOne);

		Restaurant restaurant = new Restaurant();
		restaurant.setId(restaurantId);

		List<Rating> likeRatings = new ArrayList<>();

		List<Rating> dislikeRatings = new ArrayList<>();

		when(ratingService.getRatings(restaurant.getId(), Opinion.LIKE)).thenReturn(likeRatings);

		when(ratingService.getRatings(restaurant.getId(), Opinion.DISLIKE)).thenReturn(dislikeRatings);

		assertEquals(0.0, functionalService.calcRestaurantRatings(restaurant, teammate));

	}

	@Test
	void testTeammateHasRatedTrue() {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(restaurantId);

		Rating rating = new Rating();
		rating.setRestaurantId(restaurantId);
		rating.setTeammateId(teammateIdOne);

		Teammate teammate = new Teammate();
		teammate.setId(teammateIdOne);

		List<Rating> ratings = new ArrayList<>();
		ratings.add(rating);

		when(ratingService.getRatings(restaurant.getId())).thenReturn(ratings);

		assertTrue(functionalService.teammateHasRated(teammate, restaurant));
	}

	@Test
	void testTeammateHasRatedFalse() {
		Restaurant restaurant = new Restaurant();
		restaurant.setId(restaurantId);

		Rating rating = new Rating();
		rating.setRestaurantId(restaurantId);
		rating.setTeammateId(teammateIdOne + "1");

		Teammate teammate = new Teammate();
		teammate.setId(teammateIdOne);

		List<Rating> ratings = new ArrayList<>();
		ratings.add(rating);

		when(ratingService.getRatings(restaurant.getId())).thenReturn(ratings);

		assertFalse(functionalService.teammateHasRated(teammate, restaurant));
	}

	@Test
	void testGetSimSum() {
		double simTotal = 1.0;
		List<Rating> ratings = new ArrayList<>();
		Rating rating = new Rating();
		rating.setRestaurantId(restaurantId);
		rating.setTeammateId(teammateIdTwo);
		ratings.add(rating);

		Teammate teammateOne = new Teammate();
		teammateOne.setId(teammateIdOne);

		Teammate teammateTwo = new Teammate();
		teammateTwo.setId(teammateIdTwo);

		when(teammateService.getTeammateById(rating.getTeammateId())).thenReturn(teammateTwo);
		when(teammateService.getSimilarity(teammateOne, teammateTwo)).thenReturn(simTotal);

		assertEquals(simTotal, functionalService.getSimilaritySum(ratings, teammateOne));
	}

}
