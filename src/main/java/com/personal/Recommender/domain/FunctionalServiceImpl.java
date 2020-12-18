package com.personal.Recommender.domain;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.Recommender.entity.Opinion;
import com.personal.Recommender.entity.Rating;
import com.personal.Recommender.entity.Restaurant;
import com.personal.Recommender.entity.Teammate;

@Service
public class FunctionalServiceImpl implements FunctionalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(FunctionalServiceImpl.class);

	@Autowired
	TeammateService teammateService;

	@Autowired
	RatingService ratingService;

	@Autowired
	RestaurantService restaurantService;

	@Override
	public boolean teammateHasRated(Teammate teammate, Restaurant restaurant) {
		return ratingService.getRatings(restaurant.getId()).stream()
				.anyMatch(rating -> rating.getTeammateId().equals(teammate.getId()));
	}

	@Override
	public double getSimilaritySum(List<Rating> ratings, Teammate teammate) {
		return ratings.stream().mapToDouble(rating -> teammateService.getSimilarity(teammate,
				teammateService.getTeammateById(rating.getTeammateId()))).sum();
	}

	@Override
	public double calcRestaurantRatings(Restaurant restaurant, Teammate teammate) {
		LOGGER.debug("Calculating for Restaurant {} with ID {}", restaurant.getName(), restaurant.getId());

		List<Rating> likeRatings = ratingService.getRatings(restaurant.getId(), Opinion.LIKE);
		double similarLikes = getSimilaritySum(likeRatings, teammate);
		LOGGER.debug("Similar Likes: {}", similarLikes);

		List<Rating> dislikeRatings = ratingService.getRatings(restaurant.getId(), Opinion.DISLIKE);
		double similarDislikes = getSimilaritySum(dislikeRatings, teammate);
		LOGGER.debug("Similar Dislikes: {}", similarDislikes);

		double totalRatings = likeRatings.size() + dislikeRatings.size();
		LOGGER.debug("Total Ratings: {}", totalRatings);

		if (totalRatings > 0) {
			double recommendation = (similarLikes - similarDislikes) / totalRatings;
			LOGGER.debug("Recommendation for restaurant {} had a rating of {}", restaurant.getName(), recommendation);
			return recommendation;
		} else {
			LOGGER.warn("No Ratings were found for restaurant with id {}", restaurant.getId());
			return 0;
		}
	}

}
