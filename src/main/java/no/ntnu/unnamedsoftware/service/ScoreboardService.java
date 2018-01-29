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

	public String getScoreboard(int theRussId) {
		List<Scoreboard> scoreboard = this.getScoreboardTop10(theRussId);
		Iterator it = scoreboard.iterator();
		ArrayList<ScoreboardPosition> scoreboardPosition = new ArrayList<>();
		boolean russIsInTop10 = false;
		int position = 1;
		while (it.hasNext()) {
			Scoreboard tempScore = (Scoreboard) it.next();
			scoreboardPosition.add(
					new ScoreboardPosition(
							  tempScore.getScoreboardId()
							, tempScore.getPoints()
							, position
							, tempScore.getRussId()
							));	
			position++;
			
			if (tempScore.getRussId().getRussId() == theRussId) {
				russIsInTop10 = true;
			}
		}
		if (russIsInTop10 == false) {
			Scoreboard temp = this.getSingleScoreboard(theRussId);
			scoreboardPosition.add(
					new ScoreboardPosition(
							  temp.getScoreboardId()
							, temp.getPoints()
							, this.getRussPosition(theRussId)
							, temp.getRussId()
							));	
		}
		return this.writeAsJsonString(scoreboardPosition);

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

	public List<Scoreboard> getScoreboardTop10(int theRussId) {
		return scoreboardDAO.getScoreboardTop10(theRussId);
	}

	public Integer getRussPosition(int theRussId) {
		return scoreboardDAO.getRussPosition(theRussId);
	}
	
	private Scoreboard getSingleScoreboard(int theRussId)
	{
		return scoreboardDAO.getScoreboard(theRussId);
	}

}
