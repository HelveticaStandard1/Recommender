package com.personal.Recommender.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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
import com.personal.Recommender.entity.Restaurant;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {

	@Mock
	DataRetrieval dataRetrieval;

	@InjectMocks
	RestaurantServiceImpl restaurantService;

	String id = "id";

	@Test
	void testGetRestaurants() {
		List<Restaurant> restaurants = mock(List.class);
		when(dataRetrieval.getRestaurtants()).thenReturn(restaurants);
		assertEquals(restaurants, restaurantService.getRestaurants());
	}

	@Test
	void testGetRestaurantById() {
		List<Restaurant> restaurants = new ArrayList<>();
		Restaurant restaurant = new Restaurant();
		restaurant.setId(id);
		restaurants.add(restaurant);
		when(dataRetrieval.getRestaurtants()).thenReturn(restaurants);
		assertEquals(restaurant, restaurantService.getRestaurant(id));
	}

	@Test
	void testGetRestaurantByIdNotFound() {
		List<Restaurant> restaurants = new ArrayList<>();
		when(dataRetrieval.getRestaurtants()).thenReturn(restaurants);
		try {
			restaurantService.getRestaurant(id);
			fail();
		} catch (RuntimeException ex) {
//			Should catch in test
		}
	}
}
