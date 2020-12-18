package com.personal.Recommender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.personal.Recommender.domain.FunctionalService;
import com.personal.Recommender.domain.RestaurantService;
import com.personal.Recommender.domain.TeammateService;
import com.personal.Recommender.entity.Teammate;
import com.personal.Recommender.utility.Utils;

@Component
public class Runner implements ApplicationRunner {

	private static final Logger LOGGER = LoggerFactory.getLogger(Runner.class);

	@Autowired
	TeammateService teammateService;

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	FunctionalService functionalService;

	/**
	 * Start Here...
	 */
	@Override
	public void run(ApplicationArguments args) {
		try {
			String teammateName = args.getOptionValues("name").get(0);
			Teammate teammate = teammateService.getTeammateByName(teammateName);
			LOGGER.info("Teammate ID verified as {} for name given {}", teammate.getId(), teammateName);
			LOGGER.info("Recommend Restaurants in Order are : {}", recommend(teammate));
		} catch (Exception ex) {
			LOGGER.error("Exception caught: Application failed with the message: {}", ex.getMessage());
		}
	}

	/**
	 * Log the top three restaurants for a teammate
	 * 
	 * @param requested
	 */
	public List<Entry<String, Double>> recommend(Teammate requested) {
		Map<String, Double> recommendMap = new HashMap<String, Double>();
		restaurantService.getRestaurants().stream()
				.filter(restaurant -> !functionalService.teammateHasRated(requested, restaurant))
				.forEach(restaurant -> recommendMap.put(restaurant.getName(),
						functionalService.calcRestaurantRatings(restaurant, requested)));
		Map<String, Double> orderedResult = Utils.getOrderedList(recommendMap);
		return orderedResult.entrySet().stream().limit(3).collect(Collectors.toList());
	}

}
