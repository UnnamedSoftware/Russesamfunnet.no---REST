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
	
	@Transactional
	public List<Scoreboard> getScoreboardTop10(Long theRussId) {
		Long theSchoolId = this.getSchoolId(theRussId);
		List<Scoreboard> scoreboard = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query scoreboardQuery = currentSession.createQuery("from Scoreboard s where (s.russId.schoolId.schoolId = :test) ORDER by points desc")
					.setParameter("test", theSchoolId);
			scoreboard = scoreboardQuery.list();
			ArrayList<Scoreboard> top10 = new ArrayList<>();
			int countTo10 = 0;
			Iterator it = scoreboard.iterator();
			while(it.hasNext() && countTo10 <= 10)
			{
				top10.add((Scoreboard) it.next());
				countTo10 ++;
			}
			//currentSession.close();
			return top10;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return scoreboard;
		

	}
		
	
	@Transactional
	public Integer getRussPosition(Long theRussId) {
		Long theSchoolId = this.getSchoolId(theRussId);
		int count = 0;
		try(Session currentSession = sessionFactory.openSession()){
			Query scoreboardQuery = currentSession.createQuery("from Scoreboard s where (s.russId.schoolId.schoolId = :schoolId) ORDER by points desc")
					.setParameter("schoolId", theSchoolId);
			List<Scoreboard> scoreboard = scoreboardQuery.list();
			Iterator it = scoreboard.iterator();
			while(it.hasNext())
			{
				Scoreboard tempScoreboard = (Scoreboard) it.next();
				if(tempScoreboard.getRussId().getRussId() == theRussId)
				{
					//currentSession.close();
					return count;
				}
				count++;
			}
			//currentSession.close();
			return count;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
	@Transactional
	public Scoreboard getScoreboard(Long theRussId) {
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
	

	
	
	@Transactional
	public Long getSchoolId(Long theRussId) {
		Long errorCode = new Long(9001);
		try(Session currentSession = sessionFactory.openSession()){
			
			try{
				Query russQuery = currentSession.createQuery("from Russ r where r.russId = :theRussId")
						.setParameter("theRussId", theRussId);
				Russ test = (Russ) russQuery.uniqueResult();
				if(test != null) {
				//	currentSession.close();
					return test.getSchoolId().getSchoolId();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			//currentSession.close();
			return errorCode;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return errorCode;

	}

}
