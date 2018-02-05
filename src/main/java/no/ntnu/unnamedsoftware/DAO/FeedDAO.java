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

import no.ntnu.unnamedsoftware.entity.Feed;
import no.ntnu.unnamedsoftware.entity.Russ;

@Repository
public class FeedDAO {
	

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ObjectMapper mapper;
	
	@Transactional
	public List<Feed> getSchoolFeed(int theRussId) {
		Long theSchoolId = this.getSchoolId(theRussId);
		Session currentSession = sessionFactory.openSession();
		Query feedQuery = currentSession.createQuery("from Feed f where (f.schoolId.schoolId = :schoolId)")
				.setParameter("schoolId", theSchoolId);
		List<Feed> feed = feedQuery.list();
		if(feed == null) System.out.println("DEADBEEF");
		return feed;
	}
	
	
	@Transactional
	public Long getSchoolId(int theRussId) {
		Session currentSession = sessionFactory.openSession();
		Long errorCode = new Long(9001);
		Long longRussId = new Long(theRussId);
		try{
			Query russQuery = currentSession.createQuery("from Russ r where r.russId = :theRussId")
					.setParameter("theRussId", longRussId);
			Russ test = (Russ) russQuery.uniqueResult();
			if(test != null) {
				return test.getSchoolId().getSchoolId();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return errorCode;
	}

}
