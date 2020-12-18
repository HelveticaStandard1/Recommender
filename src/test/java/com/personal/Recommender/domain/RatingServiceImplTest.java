package com.personal.Recommender.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.personal.Recommender.dao.DataRetrieval;
import com.personal.Recommender.entity.Opinion;
import com.personal.Recommender.entity.Rating;

@ExtendWith(MockitoExtension.class)
class RatingServiceImplTest {

	@Mock
	DataRetrieval dataRetrieval;

	@InjectMocks
	RatingServiceImpl ratingService;

	String restaurantId = "restaurantId";

	@Test
	void testGetRatings() {
		List<Rating> ratings = mock(List.class);
		when(dataRetrieval.getRatings()).thenReturn(ratings);
		assertEquals(ratings, ratingService.getRatings());
	}

	@Test
	void testGetRatingsByRestaurantId() {
		List<Rating> ratings = new ArrayList<>();
		Rating rating = new Rating();
		rating.setRestaurantId(restaurantId);
		ratings.add(rating);

		when(dataRetrieval.getRatings()).thenReturn(ratings);

		assertEquals(ratings, ratingService.getRatings(restaurantId));
	}

	@Test
	void testGetRatingsByRestaurantIdAndOpinion() {
		List<Rating> ratings = new ArrayList<>();
		Rating rating = new Rating();
		rating.setRestaurantId(restaurantId);
		rating.setRating(Opinion.LIKE.toString());
		ratings.add(rating);
		when(dataRetrieval.getRatings()).thenReturn(ratings);

		assertEquals(ratings, ratingService.getRatings(restaurantId, Opinion.LIKE));
	}

}
