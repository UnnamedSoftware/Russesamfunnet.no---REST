package no.ntnu.unnamedsoftware.service;

import java.io.IOException;
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

@Service
public class ScoreboardService {
	
	@Autowired
	ScoreboardDAO scoreboardDAO;
	
	public String getScoreboardTop10(int theRussId)
	{
		return scoreboardDAO.getScoreboardTop10(theRussId);
	}

}
