package no.ntnu.unnamedsoftware.DAO;

import java.io.IOException;
import java.util.ArrayList;
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
	public List<Scoreboard> getScoreboardTop10(int theRussId) {
		int theSchoolId = this.getSchoolId(theRussId);
		Session currentSession = sessionFactory.openSession();
		
		Query scoreboardQuery = currentSession.createQuery("from Scoreboard s where (s.russId.schoolId.schoolId = :test) ORDER by points desc")
				.setParameter("test", theSchoolId);
		List<Scoreboard> scoreboard = scoreboardQuery.list();
		ArrayList<Scoreboard> top10 = new ArrayList<>();
		int countTo10 = 0;
		Iterator it = scoreboard.iterator();
		while(it.hasNext() && countTo10 <= 10)
		{
			top10.add((Scoreboard) it.next());
			countTo10 ++;
		}
		return top10;
	}
		
	
	@Transactional
	public Integer getRussPosition(int theRussId) {
		int theSchoolId = this.getSchoolId(theRussId);
		Session currentSession = sessionFactory.openSession();
		
		Query scoreboardQuery = currentSession.createQuery("from Scoreboard s where (s.russId.schoolId.schoolId = :schoolId) ORDER by points desc")
				.setParameter("schoolId", theSchoolId);
		List<Scoreboard> scoreboard = scoreboardQuery.list();
		int count = 0;
		Iterator it = scoreboard.iterator();
		while(it.hasNext())
		{
			Scoreboard tempScoreboard = (Scoreboard) it.next();
			if(tempScoreboard.getRussId().getRussId() == theRussId)
			{
				return count;
			}
			count++;
		}
		return count;
		
	}
	
	@Transactional
	public Scoreboard getScoreboard(int theRussId) {
		Session currentSession = sessionFactory.openSession();
		
		Query scoreboardQuery = currentSession.createQuery("from Scoreboard s where (s.russId.russId = :russId)")
				.setParameter("russId", theRussId);
		Scoreboard scoreboard =(Scoreboard) scoreboardQuery.uniqueResult();
		return scoreboard;
		
	}
	

	
	
	@Transactional
	public int getSchoolId(int theRussId) {
		Session currentSession = sessionFactory.openSession();
		try{
			Query russQuery = currentSession.createQuery("from Russ r where r.russId = :theRussId")
					.setParameter("theRussId", theRussId);
			Russ test = (Russ) russQuery.uniqueResult();
			if(test != null) {
				return test.getSchoolId().getSchoolId();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return 9001;
	}

}
