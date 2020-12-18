package com.personal.Recommender.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.personal.Recommender.entity.Rating;
import com.personal.Recommender.entity.Restaurant;
import com.personal.Recommender.entity.Teammate;

@Repository("JsonFileDataRetrieval")
public class JsonFileDataRetrieval implements DataRetrieval {

	private static final Logger LOGGER = LoggerFactory.getLogger(JsonFileDataRetrieval.class);

	@Autowired
	ResourceLoader resourceLoader;

	@Value("${FileRoot}")
	private String root;

	@Value("${RatingsFile}")
	private String ratingsFile;

	@Value("${RestaurantsFile}")
	private String restaurantsFile;

	@Value("${TeammatesFile}")
	private String teammatesFile;

	@Cacheable("ratings")
	public List<Rating> getRatings() {
		return getJsonList(ratingsFile, Rating[].class);
	}

	@Cacheable("restaurants")
	public List<Restaurant> getRestaurtants() {
		return getJsonList(restaurantsFile, Restaurant[].class);
	}

	@Cacheable("teammates")
	public List<Teammate> getTeammates() {
		return getJsonList(teammatesFile, Teammate[].class);
	}

	private <T> List<T> getJsonList(String fileName, Class<T[]> klazz) {
		Gson gson = new Gson();
		T[] arr = gson.fromJson(getFileReader(fileName), klazz);
		return Arrays.asList(arr);
	}

	private BufferedReader getFileReader(String fileName) {
		try {
			return Files.newBufferedReader(getFile(fileName).toPath());
		} catch (IOException e) {
			LOGGER.error("IOException caught: Failed to retrieve file {}", fileName);
			throw new RuntimeException();
		}
	}

	private File getFile(String fileName) throws IOException {
		return resourceLoader.getResource(root + fileName).getFile();
	}

}
