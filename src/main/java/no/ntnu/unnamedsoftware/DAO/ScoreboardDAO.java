package no.ntnu.unnamedsoftware.DAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.entity.Scoreboard;
import no.ntnu.unnamedsoftware.entity.Russ;

@Repository
public class ScoreboardDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	SchoolDAO schoolDAO;
	
	@Transactional
	public List<Scoreboard> getSchoolScoreboard(Long theRussId) {
		Long theSchoolId = schoolDAO.getSchoolId(theRussId);
		List<Scoreboard> scoreboard = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query scoreboardQuery = currentSession.createQuery("from Scoreboard s where (s.russId.schoolId.schoolId = :test) ORDER by points desc")
					.setParameter("test", theSchoolId);
			scoreboard = scoreboardQuery.list();
			
			//currentSession.close();
			return scoreboard;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return scoreboard;
	}

	@Transactional
	public Scoreboard getSingleScoreboard(Long theRussId) {
		try(Session currentSession = sessionFactory.openSession()){
			Query scoreboardQuery = currentSession.createQuery("from Scoreboard s where (s.russId.russId = :russId)")
				.setParameter("russId", theRussId);
			Scoreboard scoreboard = (Scoreboard) scoreboardQuery.uniqueResult();
			//currentSession.close();
			return scoreboard;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
