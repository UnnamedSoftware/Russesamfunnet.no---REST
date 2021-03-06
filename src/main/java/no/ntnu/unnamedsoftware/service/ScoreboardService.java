package no.ntnu.unnamedsoftware.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.DAO.GroupDAO;
import no.ntnu.unnamedsoftware.DAO.ScoreboardDAO;
import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.Scoreboard;
import no.ntnu.unnamedsoftware.entity.ScoreboardPosition;

@Service
public class ScoreboardService {

	@Autowired
	ScoreboardDAO scoreboardDAO;

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	GroupService groupService;
	
	@Autowired
	GroupDAO groupDAO;

	public String getScoreboardTop10(Long theRussId) {
		List<Scoreboard> scoreboard = scoreboardDAO.getSchoolScoreboard(theRussId);
		ArrayList<Scoreboard> top10 = new ArrayList<>();
		int countTo10 = 0;
		Iterator it = scoreboard.iterator();
		while (it.hasNext() && countTo10 < 10) {
			top10.add((Scoreboard) it.next());
			countTo10++;
		}

		return this.writeAsJsonString(getScoreboardPosition(top10, theRussId));

	}
	/**
	 * Takes a list of scoreboard and the russId of the user and creates a list of 
	 * ScoreboardPostition where the user is added at the end if he is not in the
	 * top list.
	 * @param scoreboard
	 * @param theRussId
	 * @return List<ScoreboardPositio>
	 */
	private List<ScoreboardPosition> getScoreboardPosition(List<Scoreboard> scoreboard, Long theRussId) {
		Iterator it = scoreboard.iterator();
		ArrayList<ScoreboardPosition> scoreboardPosition = new ArrayList<>();
		boolean russIsInTopList = false;
		int position = 1;
		while (it.hasNext()) {
			Scoreboard tempScore = (Scoreboard) it.next();
			scoreboardPosition.add(new ScoreboardPosition(
					tempScore.getScoreboardId(),
					tempScore.getPoints(),
					position,
					tempScore.getRussId()));
			position++;
			if (tempScore.getRussId().getRussId().equals(theRussId)) {
				russIsInTopList = true;
			}
		}
		if (russIsInTopList == false) {
			Scoreboard temp = this.getSingleScoreboard(theRussId);
			scoreboardPosition.add(new ScoreboardPosition(temp.getScoreboardId(), temp.getPoints(),
					this.getRussPosition(theRussId), temp.getRussId()));
		}
		return scoreboardPosition;
	}

	private String writeAsJsonString(List<ScoreboardPosition> scoreboard) {
		String scoreboardInJsonString = null;
		try {
			scoreboardInJsonString = mapper.writeValueAsString(scoreboard);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return scoreboardInJsonString;
	}

	public Integer getRussPosition(Long theRussId) {
		int count = 1;
		Iterator it = scoreboardDAO.getSchoolScoreboard(theRussId).iterator();
		while(it.hasNext())
		{
			Scoreboard tempScoreboard = (Scoreboard) it.next();
			if(tempScoreboard.getRussId().getRussId().equals(theRussId))
			{
				return count;
			}
			count++;
		}
		//currentSession.close();
		return count;
	}

	private Scoreboard getSingleScoreboard(Long theRussId) {
		return scoreboardDAO.getSingleScoreboard(theRussId);
	}

	public String getSchoolScoreboard(Long theRussId) {
		return writeAsJsonString(getScoreboardPosition(scoreboardDAO.getSchoolScoreboard(theRussId), theRussId));
	}
	
	
	public String getScoreboardGroup(Long russId, Long groupId)
	{
		if(groupService.isPartOfGroup(russId, groupId))
		{
			return writeAsJsonString(getScoreboardPosition(scoreboardDAO.getScoreboardGroup(groupId), russId));
		}
		return null;
	}
	
	public String getScoreboardGroupTop3(Long russId, Long groupId)
	{
		
		if(groupService.isPartOfGroup(russId, groupId))
		{
			return writeAsJsonString(getScoreboardPosition(getScoreboardTopList(scoreboardDAO.getScoreboardGroup(groupId), 3), russId));
		}
		return null;
	}
	
	public List<ScoreboardPosition> ScoreboardGroupTop3(Long russId, Long groupId)
	{
		
		if(groupService.isPartOfGroup(russId, groupId))
		{
			return getScoreboardPosition(getScoreboardTopList(scoreboardDAO.getScoreboardGroup(groupId), 3), russId);
		}
		return null;
	}
	
	public List<Scoreboard> getScoreboardTopList(List<Scoreboard> inList, int numberOfScores) {
		ArrayList<Scoreboard> top = new ArrayList<>();
		int count = 0;
		Iterator it = inList.iterator();
		while (it.hasNext() && count < numberOfScores) {
			top.add((Scoreboard) it.next());
			count++;
		}
		return top;
	}

	
	public String getUnsortedScoreboardGroup(Long russId, Long groupId)
	{
		if(groupService.isPartOfGroup(russId, groupId))
		{
			List<Russ> groupRuss = groupDAO.getGroupRuss(groupId);
			List<Scoreboard> groupScoreboard = new ArrayList<>();
			Iterator it = groupRuss.iterator();
			while(it.hasNext())
			{
				groupScoreboard.add(scoreboardDAO.getSingleScoreboard((Long) it.next()));
			}
			return writeAsJsonString(this.getScoreboardPosition(groupScoreboard, russId));
		}
		return null;
	}
	
}
