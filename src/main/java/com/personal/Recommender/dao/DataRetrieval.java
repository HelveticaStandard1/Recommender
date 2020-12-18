package com.personal.Recommender.dao;

import java.util.List;

import com.personal.Recommender.entity.Rating;
import com.personal.Recommender.entity.Restaurant;
import com.personal.Recommender.entity.Teammate;

public interface DataRetrieval {

	public List<Rating> getRatings();

	public List<Restaurant> getRestaurtants();

	public List<Teammate> getTeammates();

}
