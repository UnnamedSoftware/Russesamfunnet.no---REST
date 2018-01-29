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
	public String getScoreboardTop10(int theRussId) {
		int theSchoolId = this.getSchoolId(theRussId);
		Session currentSession = sessionFactory.openSession();
		String scoreboardInJsonString = null;
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
		try {
			scoreboardInJsonString = mapper.writeValueAsString(top10);
		} catch (JsonGenerationException e) {e.printStackTrace();} 
		  catch (JsonMappingException e) { e.printStackTrace();} 
		  catch (IOException e) { e.printStackTrace();}
		return scoreboardInJsonString;
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
