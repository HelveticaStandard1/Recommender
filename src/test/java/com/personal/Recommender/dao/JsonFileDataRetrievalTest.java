package com.personal.Recommender.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import com.personal.Recommender.SpringBootTestConfig;
import com.personal.Recommender.entity.Rating;
import com.personal.Recommender.entity.Restaurant;
import com.personal.Recommender.entity.Teammate;

class JsonFileDataRetrievalTest extends SpringBootTestConfig {

	@Autowired
	@Qualifier("JsonFileDataRetrieval")
	DataRetrieval dataRetrieval;

	@BeforeEach
	public void setup() throws IOException {
	}

	@Test
	void testGetRatings() {
		List<Rating> ratings = dataRetrieval.getRatings();
		assertTrue(ratings.size() == 1);
		assertEquals("testTeammateId", ratings.get(0).getTeammateId());
		assertEquals("testRestaurantId", ratings.get(0).getRestaurantId());
		assertEquals("rating", ratings.get(0).getRating());
	}

	@Test
	void testGetTeammates() {
		List<Teammate> teammates = dataRetrieval.getTeammates();
		assertTrue(teammates.size() == 1);
		assertEquals("id", teammates.get(0).getId());
		assertEquals("Test tester", teammates.get(0).getName());
	}

	@Test
	void testGetRestaurants() {
		List<Restaurant> restaurants = dataRetrieval.getRestaurtants();
		assertTrue(restaurants.size() == 1);
		assertEquals("RestaurantName", restaurants.get(0).getName());
		assertEquals("id", restaurants.get(0).getId());
	}

	@Test
	void testExceptionRetrieval() throws IOException {
//		Resource mockResource = mock(Resource.class);
//		when(mockResource.getFile()).thenThrow(new IOException());
//		doReturn(mockResource).when(resourceLoader).getResource(any());
//		try {
//			dataRetrieval.getRatings();
//			fail();
//		} catch (RuntimeException ex) {
//		}
	}

}
