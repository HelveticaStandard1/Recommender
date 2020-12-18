package com.personal.Recommender.entity;

public class Rating {

	private String teammateId;

	private String restaurantId;

	private String rating;

	public String getTeammateId() {
		return teammateId;
	}

	public void setTeammateId(String teammateId) {
		this.teammateId = teammateId;
	}

	public String getRestaurantId() {
		return restaurantId;
	}

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

}
