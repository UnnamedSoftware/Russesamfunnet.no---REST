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
import no.ntnu.unnamedsoftware.entity.School;

@Repository
public class FeedDAO {
	

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	RussDAO russDAO;
	
	@Autowired
	ObjectMapper mapper;
	
	@Transactional
	public List<Feed> getSchoolFeed(Long theRussId) {
		Long theSchoolId = this.getSchoolId(theRussId);
		List<Feed> feed = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query feedQuery = currentSession.createQuery("from Feed f where (f.schoolId.schoolId = :schoolId)")
					.setParameter("schoolId", theSchoolId);
			feed = feedQuery.list();
			if(feed == null) System.out.println("DEADBEEF");
			return feed;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return feed;
	}
	
	
	@Transactional
	public Long getSchoolId(Long theRussId) {
		Long errorCode = new Long(9001);
		try(Session currentSession = sessionFactory.openSession()){
			Query russQuery = currentSession.createQuery("from Russ r where r.russId = :theRussId")
					.setParameter("theRussId", theRussId);
			Russ test = (Russ) russQuery.uniqueResult();
			if(test != null) {
				return test.getSchoolId().getSchoolId();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return errorCode;
	}
	@Transactional
	public List<Feed> postFeed(Long russId, String message)
	{	
		try(Session currentSession = sessionFactory.openSession()){
		Feed feed = new Feed();
		feed.setMessage(message);
		feed.setType("School");
		Russ russ = russDAO.getUserRuss(russId);
		School school = this.getSchoolObject(russId);
		feed.setRussId(russ);
		currentSession.save(feed);
		return getSchoolFeed(russId);
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public School getSchoolObject(Long theRussId) {
		Russ russ = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query russQuery = currentSession.createQuery("from Russ r where r.russId = :theRussId")
					.setParameter("theRussId", theRussId);
			russ = (Russ) russQuery.uniqueResult();
			if (russ == null)
			{
				//do something to prevent nullPointer
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return russ.getSchoolId();
	}


}
