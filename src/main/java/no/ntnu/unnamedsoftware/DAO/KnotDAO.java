package no.ntnu.unnamedsoftware.DAO;

import java.io.IOException;
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

import no.ntnu.unnamedsoftware.entity.Completed;
import no.ntnu.unnamedsoftware.entity.Knots;
import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.School;

@Repository
public class KnotDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	ObjectMapper mapper;

	@Transactional
	public String getKnots(int theSchoolId) {
		Session currentSession = sessionFactory.openSession();
		String knotsInJsonString = null;
		Query knotQuery = currentSession.createQuery("from Knots k where (k.schoolId IS NULL) OR (k.schoolId.schoolId = :test)")
				.setParameter("test", theSchoolId);
		List<Knots> knots = knotQuery.list();
		try {
			knotsInJsonString = mapper.writeValueAsString(knots);
		} catch (JsonGenerationException e) {e.printStackTrace();} 
		  catch (JsonMappingException e) { e.printStackTrace();} 
		  catch (IOException e) { e.printStackTrace();}
		return knotsInJsonString;
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
	
	@Transactional
	public void getCompleted(int theRussId) {
		Session currentSession = sessionFactory.openSession();
		Query completedQuery = currentSession.createQuery("from Completed c where c.russId.russId = :theRussId")
				.setParameter("theRussId", theRussId);
		List<Completed> completed = completedQuery.list();
		System.out.println(completed.size());
		for(Completed c: completed) {
			System.out.println(c.getRussId().getFirstName());
		}
	}
	
	@Transactional
	public List<Knots> getKnotsList(int theSchoolId) {
		Session currentSession = sessionFactory.openSession();
		Query knotQuery = currentSession.createQuery("from Knots k where (k.schoolId IS NULL) OR (k.schoolId.schoolId = :test)")
				.setParameter("test", theSchoolId);
		List<Knots> knots = knotQuery.list();
		return knots;
	}
	
	@Transactional
	public List<Completed> getCompletedKnots(int theRussId){	
		Session currentSession = sessionFactory.openSession();
		Query completedQuery = currentSession.createQuery("from Completed c where c.russId.russId = :theRussId")
				.setParameter("theRussId", theRussId);
		List<Completed> completed = completedQuery.list();
		return completed;
	}

	@Transactional
	public String registerCompletedKnot(Russ theRussId, Knots theKnotId, 
										Russ theWitnessId1, Russ theWitnessId2) {
		Session currentSession = sessionFactory.openSession();
		Completed newCompleted = new Completed();
		newCompleted.setRussId(theRussId);
	    newCompleted.setKnotId(theKnotId);
	    newCompleted.setWitnessId1(theWitnessId1);
	    newCompleted.setWitnessId2(theWitnessId2);
	    currentSession.save(newCompleted);
		
		return "OK";
	}
	
}





















