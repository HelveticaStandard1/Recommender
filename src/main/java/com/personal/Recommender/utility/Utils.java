package com.personal.Recommender.utility;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.personal.Recommender.entity.Rating;

public class Utils {

	/**
	 * Utility method to convert a Map to an ordered list.
	 * 
	 * @param recommendMap
	 * @return
	 */
	public static Map<String, Double> getOrderedList(Map<String, Double> recommendMap) {
		return recommendMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue,
						LinkedHashMap::new));
	}

	/**
	 * Formulat to calculate the similarity index
	 * @param unionLL
	 * @param unionDD
	 * @param unionLD
	 * @param unionDL
	 * @param totalOne
	 * @param totalTwo
	 * @return
	 */
	public static double calcSim(int unionLL, int unionDD, int unionLD, int unionDL, int totalOne, int totalTwo) {
		int numerator = unionLL + unionDD - unionLD - unionDL;
		int denominator = totalOne + totalTwo;
		return (double) numerator / denominator;
	}

	/**
	 * Given two list of ratings, find the union between the two based on Restaurant
	 * ID in each rating
	 * 
	 * @param teammateOneLikes
	 * @param teammateTwoLikes
	 * @return
	 */
	public static List<Rating> getUnionRating(List<Rating> teammateOneLikes, List<Rating> teammateTwoLikes) {
		return teammateOneLikes.stream().filter(
				one -> teammateTwoLikes.stream().anyMatch(two -> one.getRestaurantId().equals(two.getRestaurantId())))
				.collect(Collectors.toList());
	}

}
