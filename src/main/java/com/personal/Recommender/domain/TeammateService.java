package com.personal.Recommender.domain;

import java.util.List;

import com.personal.Recommender.entity.Opinion;
import com.personal.Recommender.entity.Rating;
import com.personal.Recommender.entity.Teammate;

public interface TeammateService {

	/**
	 * Get a list of ratings based on a teammates opinion. Aims to grab all ratings
	 * that have an opinion by the teammate requested.
	 * 
	 * @param teammate
	 * @param opinion
	 * @return
	 */
	public List<Rating> getTeammateOpinions(Teammate teammate, Opinion opinion);

	/**
	 * Get Teammate object by name
	 * @param name
	 * @return
	 */
	public Teammate getTeammateByName(String name);
	
	/**
	 * Get Teammate Object by Id
	 * @param id
	 * @return
	 */
	public Teammate getTeammateById(String id);

	/**
	 * Get Unfiltered list of teammates
	 * @return
	 */
	public List<Teammate> getTeammates();

	/**
	 * Find Similarity index between two teammates
	 * @param teammateOne
	 * @param teammateTwo
	 * @return
	 */
	public double getSimilarity(Teammate teammateOne, Teammate teammateTwo);
}
