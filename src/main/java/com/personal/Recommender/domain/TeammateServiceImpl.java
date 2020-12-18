package com.personal.Recommender.domain;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.personal.Recommender.dao.DataRetrieval;
import com.personal.Recommender.entity.Opinion;
import com.personal.Recommender.entity.Rating;
import com.personal.Recommender.entity.Teammate;
import com.personal.Recommender.utility.Utils;

@Service
public class TeammateServiceImpl implements TeammateService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TeammateServiceImpl.class);

	@Autowired
	DataRetrieval dataRetrieval;

	@Autowired
	RatingService ratingService;

	public List<Rating> getTeammateOpinions(Teammate teammate, Opinion opinion) {
		return ratingService.getRatings().stream().filter(rating -> rating.getTeammateId().equals(teammate.getId()))
				.filter(rating -> rating.getRating().equals(opinion.toString())).collect(Collectors.toList());
	}

	@Override
	public Teammate getTeammateByName(String name) {
		try {
			return getTeammates().stream().filter(teammate -> teammate.getName().equalsIgnoreCase(name)).limit(1)
					.collect(Collectors.toList()).get(0);
		} catch (IndexOutOfBoundsException ex) {
			throw new RuntimeException("Failed to find teammate based on name with given: " + name);
		}
	}

	@Override
	public Teammate getTeammateById(String id) {
		try {
			return getTeammates().stream().filter(teammate -> teammate.getId().equalsIgnoreCase(id)).limit(1)
					.collect(Collectors.toList()).get(0);
		} catch (IndexOutOfBoundsException ex) {
			throw new RuntimeException("Failed to find teammate based on id with given: " + id);
		}
	}

	@Override
	public List<Teammate> getTeammates() {
		return dataRetrieval.getTeammates();
	}

	@Override
	public double getSimilarity(Teammate teammateOne, Teammate teammateTwo) {

		List<Rating> teammateOneLikes = getTeammateOpinions(teammateOne, Opinion.LIKE);
		List<Rating> teammateOneDislikes = getTeammateOpinions(teammateOne, Opinion.DISLIKE);
		int totalOne = teammateOneLikes.size() + teammateOneDislikes.size();

		List<Rating> teammateTwoLikes = getTeammateOpinions(teammateTwo, Opinion.LIKE);
		List<Rating> teammateTwoDislikes = getTeammateOpinions(teammateTwo, Opinion.DISLIKE);
		int totalTwo = teammateTwoLikes.size() + teammateTwoDislikes.size();

		List<Rating> unionedLL = Utils.getUnionRating(teammateOneLikes, teammateTwoLikes);
		List<Rating> unionedDD = Utils.getUnionRating(teammateOneDislikes, teammateTwoDislikes);
		List<Rating> unionedLD = Utils.getUnionRating(teammateOneLikes, teammateTwoDislikes);
		List<Rating> unionedDL = Utils.getUnionRating(teammateOneDislikes, teammateTwoLikes);

		double calculated = Utils.calcSim(unionedLL.size(), unionedDD.size(), unionedLD.size(), unionedDL.size(),
				totalOne, totalTwo);
		LOGGER.debug("Calculated Similarity between Teammate One ID {} and Teammate Two ID {} as {}",
				teammateOne.getId(), teammateTwo.getId(), calculated);
		return calculated;

	}

}
