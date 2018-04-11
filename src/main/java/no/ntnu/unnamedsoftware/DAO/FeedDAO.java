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
	SchoolDAO schoolDAO;
	
	@Autowired
	GroupDAO groupDAO;
	
	@Autowired
	ObjectMapper mapper;
	
	@Transactional
	public List<Feed> getAllFeed() {
		List<Feed> feed = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query feedQuery = currentSession.createQuery("from Feed");
			feed = feedQuery.list();
			return feed;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return feed;
	}
	
	@Transactional
	public List<Feed> getGroupFeed(Long groupId) {
		List<Feed> feed = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query feedQuery = currentSession.createQuery("from Feed f where (f.groupId.groupId = :groupId)")
					.setParameter("groupId", groupId);
			feed = feedQuery.list();
			return feed;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return feed;
	}
	
	@Transactional
	public List<Feed> getSchoolFeed(Long theRussId) {
		Long theSchoolId = schoolDAO.getSchoolId(theRussId);
		List<Feed> feed = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query feedQuery = currentSession.createQuery("from Feed f where (f.schoolId.schoolId = :schoolId)")
					.setParameter("schoolId", theSchoolId);
			feed = feedQuery.list();
			return feed;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return feed;
	}
	
	@Transactional
	public Feed postFeed(Long russId, String message)
	{	
		try(Session currentSession = sessionFactory.openSession()){
		Feed feed = new Feed();
		feed.setMessage(message);
		feed.setType("School");
		Russ russ = russDAO.getUserRussFromId(russId);
		School school = schoolDAO.getSchoolObject(russId);
		feed.setRussId(russ);
		feed.setSchoolId(school);
		currentSession.save(feed);
		return feed;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public Feed postFeedToGroup(Long russId, String message, Long groupId)
	{	
		try(Session currentSession = sessionFactory.openSession()){
		Feed feed = new Feed();
		feed.setMessage(message);
		feed.setType("Group");
		Russ russ = russDAO.getUserRussFromId(russId);
		feed.setRussId(russ);
		feed.setGroupId(groupDAO.getGroup(groupId));
		currentSession.save(feed);
		return feed;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Transactional
	public String deleteMessage(Long russId, Long feedId) {
		try (Session currentSession = sessionFactory.openSession()) {
			Query deleteQuery = currentSession
					.createSQLQuery("delete from feed where feed.feed_id=" + feedId + " and feed.russ_id=" + russId);
			int result = deleteQuery.executeUpdate();
				if(result > 0)
				{
					return "Message successfully deleted";
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "An error occured";
	}
}
