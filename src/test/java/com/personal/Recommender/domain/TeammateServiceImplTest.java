package com.personal.Recommender.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.personal.Recommender.dao.DataRetrieval;
import com.personal.Recommender.entity.Opinion;
import com.personal.Recommender.entity.Rating;
import com.personal.Recommender.entity.Teammate;
import com.personal.Recommender.utility.Utils;

@ExtendWith(MockitoExtension.class)
class TeammateServiceImplTest {

	String teammateId = "teammateId";

	@Mock
	DataRetrieval dataRetrieval;

	@Mock
	RatingService ratingService;

	@InjectMocks
	@Spy
	TeammateServiceImpl teammateService;

	@Test
	void testGetTeammateOpinions() {
		List<Rating> ratings = new ArrayList<>();
		Rating rating = new Rating();
		rating.setTeammateId(teammateId);
		rating.setRating(Opinion.LIKE.toString());
		ratings.add(rating);

		Teammate teammate = new Teammate();
		teammate.setId(teammateId);

		when(ratingService.getRatings()).thenReturn(ratings);
		assertEquals(ratings, teammateService.getTeammateOpinions(teammate, Opinion.LIKE));
	}

	@Test
	void testGetTeammateByName() {
		String name = "name";
		List<Teammate> teammates = new ArrayList<>();
		Teammate teammate = new Teammate();
		teammate.setName(name);
		teammates.add(teammate);
		when(dataRetrieval.getTeammates()).thenReturn(teammates);
		assertEquals(teammate, teammateService.getTeammateByName(name));
	}

	@Test
	void testGetTeammateByNameNotFound() {
		String name = "name";
		List<Teammate> teammates = new ArrayList<>();
		when(dataRetrieval.getTeammates()).thenReturn(teammates);
		try {
			teammateService.getTeammateByName(name);
			fail();
		} catch (RuntimeException ex) {
//			Should catch in test 
		}
	}

	@Test
	void testGetTeammateById() {
		String id = "id";
		List<Teammate> teammates = new ArrayList<>();
		Teammate teammate = new Teammate();
		teammate.setId(id);
		teammates.add(teammate);
		when(dataRetrieval.getTeammates()).thenReturn(teammates);
		assertEquals(teammate, teammateService.getTeammateById(id));
	}

	@Test
	void testGetTeammateByIdNotListed() {
		String id = "id";
		List<Teammate> teammates = new ArrayList<>();
		when(dataRetrieval.getTeammates()).thenReturn(teammates);
		try {
			teammateService.getTeammateById(id);
			fail();
		} catch (RuntimeException ex) {
//			Should catch in test 
		}
	}

	@Test
	void testGetSimilarity() {
		double calculated = 1.0;
		int teammaeOneLikesSize = 5;
		int teammateTwoLikesSize = 8;
		int teammateOneDislikesSize = 5;
		int teammateTwoDislikesSize = 7;
		int unionedLLSize = 4;
		int unionedDDSize = 6;
		int unionedLDSize = 8;
		int unionedDLSize = 10;
		Teammate teammateOne = new Teammate();
		Teammate teammateTwo = new Teammate();

		List<Rating> teammateOneLikes = mock(List.class);
		List<Rating> teammateOneDislikes = mock(List.class);
		when(teammateOneLikes.size()).thenReturn(teammaeOneLikesSize);
		when(teammateOneDislikes.size()).thenReturn(teammateOneDislikesSize);
		List<Rating> teammateTwoLikes = mock(List.class);
		List<Rating> teammateTwoDislikes = mock(List.class);
		when(teammateTwoLikes.size()).thenReturn(teammateTwoLikesSize);
		when(teammateTwoDislikes.size()).thenReturn(teammateTwoDislikesSize);
		List<Rating> unionedLL = mock(List.class);
		when(unionedLL.size()).thenReturn(unionedLLSize);
		List<Rating> unionedDD = mock(List.class);
		when(unionedDD.size()).thenReturn(unionedDDSize);
		List<Rating> unionedLD = mock(List.class);
		when(unionedLD.size()).thenReturn(unionedLDSize);
		List<Rating> unionedDL = mock(List.class);
		when(unionedDL.size()).thenReturn(unionedDLSize);

		doReturn(teammateOneLikes).when(teammateService).getTeammateOpinions(teammateOne, Opinion.LIKE);
		doReturn(teammateOneDislikes).when(teammateService).getTeammateOpinions(teammateOne, Opinion.DISLIKE);

		doReturn(teammateTwoLikes).when(teammateService).getTeammateOpinions(teammateTwo, Opinion.LIKE);
		doReturn(teammateTwoDislikes).when(teammateService).getTeammateOpinions(teammateTwo, Opinion.DISLIKE);

		try (MockedStatic<Utils> mockedUtils = mockStatic(Utils.class)) {
			mockedUtils.when(() -> Utils.getUnionRating(teammateOneLikes, teammateTwoLikes)).thenReturn(unionedLL);
			mockedUtils.when(() -> Utils.getUnionRating(teammateOneDislikes, teammateTwoDislikes))
					.thenReturn(unionedDD);
			mockedUtils.when(() -> Utils.getUnionRating(teammateOneLikes, teammateTwoDislikes)).thenReturn(unionedLD);
			mockedUtils.when(() -> Utils.getUnionRating(teammateOneDislikes, teammateTwoLikes)).thenReturn(unionedDL);
			mockedUtils.when(() -> Utils.calcSim(unionedLLSize, unionedDDSize, unionedLDSize, unionedDLSize,
					teammaeOneLikesSize + teammateOneDislikesSize, teammateTwoLikesSize + teammateTwoDislikesSize))
					.thenReturn(calculated);

			assertEquals(calculated, teammateService.getSimilarity(teammateOne, teammateTwo));

			mockedUtils.verify(() -> Utils.getUnionRating(teammateOneLikes, teammateTwoLikes));
			mockedUtils.verify(() -> Utils.getUnionRating(teammateOneDislikes, teammateTwoDislikes));
			mockedUtils.verify(() -> Utils.getUnionRating(teammateOneLikes, teammateTwoDislikes));
			mockedUtils.verify(() -> Utils.getUnionRating(teammateOneDislikes, teammateTwoLikes));
			mockedUtils.verify(() -> Utils.calcSim(unionedLLSize, unionedDDSize, unionedLDSize, unionedDLSize,
					teammaeOneLikesSize + teammateOneDislikesSize, teammateTwoLikesSize + teammateTwoDislikesSize));

		}

	}

}
