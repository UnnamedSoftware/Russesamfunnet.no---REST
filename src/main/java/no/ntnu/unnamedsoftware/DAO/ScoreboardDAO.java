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
	
	/**
	 * @param groupId
	 * @return
	 */
	@Transactional
	public List<Scoreboard> getScoreboardGroup(Long groupId) {
		List<Scoreboard> scoreboard = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query scoreboardQuery = currentSession.createQuery(
					"select sb from Scoreboard sb, RussGroup rg "
					//+ "join sb.russId on rg.russId"
					+ "where (rg.groupId = " + groupId + ")"
					+ "and (sb.russId = rg.russId) "
					+ "ORDER by points desc");
					//.setParameter("groupId", groupId);
		/*	Query scoreboardQuery = currentSession.createSQLQuery("SELECT scoreboard.russ_id, russ_group.russ_id "
															+ "FROM scoreboard "
															+ "INNER JOIN russ_group ON scoreboard.russ_id = russ_group.russ_id "
															+ "WHERE russ_group.group_id = " + groupId + " "
															+ "ORDER BY scoreboard.points DESC");
					//.setParameter("groupId", groupId);
		
			System.out.println(scoreboardQuery.getQueryString());*/
			scoreboard = scoreboardQuery.list();
			
			//currentSession.close();
			return scoreboard;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return scoreboard;
	}
	
	
	@Transactional
	public Scoreboard createScoreboard(Russ russ)
	{
		Scoreboard scoreboard = new Scoreboard();
		try(Session currentSession = sessionFactory.openSession()){
			scoreboard.setRussId(russ);
			scoreboard.setPoints(0);
			currentSession.save(scoreboard);
			
			//currentSession.close();
			return scoreboard;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return scoreboard;
	}
	
	@Transactional
	public Scoreboard incrementScoreboard(Russ russ)
	{
		Scoreboard scoreboard = this.getSingleScoreboard(russ.getRussId());
		try (Session currentSession = sessionFactory.openSession()) {
			// Query addWitness = currentSession.createSQLQuery("UPDATE completed SET
			// witness_id1 = "
			// + theWitness.getRussId() + " WHERE completed_id = " + completedKnotId);
			// addWitness.executeUpdate();

			int points = scoreboard.getPoints() + 1;
			Query theQuery = currentSession		
					.createSQLQuery("UPDATE scoreboard SET points='" + points + "' WHERE russ_id=" + russ.getRussId());
			theQuery.executeUpdate();
		System.out.println("Increment");
			return scoreboard;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return scoreboard;
	}
	
	@Transactional
	public Scoreboard decreaseScoreboard(Russ russ)
	{
		Scoreboard scoreboard = this.getSingleScoreboard(russ.getRussId());
		try (Session currentSession = sessionFactory.openSession()) {
			// Query addWitness = currentSession.createSQLQuery("UPDATE completed SET
			// witness_id1 = "
			// + theWitness.getRussId() + " WHERE completed_id = " + completedKnotId);
			// addWitness.executeUpdate();

			int points = scoreboard.getPoints() - 1;
			Query theQuery = currentSession		
					.createSQLQuery("UPDATE scoreboard SET points='" + points + "' WHERE russ_id=" + russ.getRussId());
			theQuery.executeUpdate();
		System.out.println("Increment");
			return scoreboard;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return scoreboard;
	}
	

}
