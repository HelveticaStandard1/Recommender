package com.personal.Recommender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.ApplicationArguments;

import com.personal.Recommender.domain.FunctionalService;
import com.personal.Recommender.domain.RestaurantService;
import com.personal.Recommender.domain.TeammateService;
import com.personal.Recommender.entity.Restaurant;
import com.personal.Recommender.entity.Teammate;

@ExtendWith(MockitoExtension.class)
class RunnerTest {

	@Mock
	TeammateService teammateService;

	@Mock
	RestaurantService restaurantService;

	@Mock
	FunctionalService functionalService;

	@Mock
	ApplicationArguments args;

	@InjectMocks
	@Spy
	Runner runner;

	String name = "name";

	String nameTwo = "NameTwo";
	String nameThree = "NameThree";
	String nameFour = "NameFour";

	double ratingTwo = 4.0;
	double ratingThree = 3.0;
	double ratingFour = 2.0;

	@Test
	void testRunnerEntry() throws Exception {
		List<Entry<String, Double>> recommendation = mock(List.class);
		Teammate teammate = new Teammate();
		List<String> arguments = new ArrayList<>();
		arguments.add(name);
		when(args.getOptionValues("name")).thenReturn(arguments);
		when(teammateService.getTeammateByName(name)).thenReturn(teammate);
		doReturn(recommendation).when(runner).recommend(teammate);

		runner.run(args);

		verify(runner).recommend(teammate);
	}

	@Test
	void testRunnerEntryFailureShouldFailGracefully() throws Exception {
		List<Entry<String, Double>> recommendation = mock(List.class);
		Teammate teammate = new Teammate();
		List<String> arguments = new ArrayList<>();
		arguments.add(name);
		when(args.getOptionValues("name")).thenReturn(arguments);
		when(teammateService.getTeammateByName(name)).thenThrow(new RuntimeException("Test"));
		runner.run(args);
	}

	@Test
	void testRecommend() {
		Teammate teammate = new Teammate();
		List<Restaurant> restaurants = new ArrayList();
		Restaurant restaurantOne = new Restaurant();
		Restaurant restaurantTwo = new Restaurant();
		restaurantTwo.setName(nameTwo);
		Restaurant restaurantThree = new Restaurant();
		restaurantThree.setName(nameThree);
		Restaurant restaurantFour = new Restaurant();
		restaurantFour.setName(nameFour);

		restaurants.add(restaurantOne);
		restaurants.add(restaurantTwo);
		restaurants.add(restaurantThree);
		restaurants.add(restaurantFour);

		when(restaurantService.getRestaurants()).thenReturn(restaurants);

		when(functionalService.teammateHasRated(teammate, restaurantOne)).thenReturn(true);
		when(functionalService.teammateHasRated(teammate, restaurantTwo)).thenReturn(false);
		when(functionalService.teammateHasRated(teammate, restaurantThree)).thenReturn(false);
		when(functionalService.teammateHasRated(teammate, restaurantFour)).thenReturn(false);

		when(functionalService.calcRestaurantRatings(restaurantTwo, teammate)).thenReturn(ratingTwo);
		when(functionalService.calcRestaurantRatings(restaurantThree, teammate)).thenReturn(ratingThree);
		when(functionalService.calcRestaurantRatings(restaurantFour, teammate)).thenReturn(ratingFour);

		List<Entry<String, Double>> result = runner.recommend(teammate);

		assertEquals(ratingTwo, result.get(0).getValue());
		assertEquals(ratingThree, result.get(1).getValue());
		assertEquals(ratingFour, result.get(2).getValue());
	}

}
