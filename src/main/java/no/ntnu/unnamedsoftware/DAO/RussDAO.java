package no.ntnu.unnamedsoftware.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import no.ntnu.unnamedsoftware.entity.Feed;
import no.ntnu.unnamedsoftware.entity.Russ;



@Repository
public class RussDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<Russ> getRuss()
	{
	Session currentSession = sessionFactory.openSession();
	
	Query theQuery = currentSession.
			createQuery("from Russ r"); 
	
	List<Russ> userInfo = theQuery.list();;
	currentSession.close();
	return userInfo;
	//return JSON
	}
	
	
	public Russ getUserRuss(Long theRussId)
	{
		Session currentSession = sessionFactory.openSession();
		Query russQuery = currentSession.createQuery("from Russ r where (r.russId = :russId)")
				.setParameter("russId", theRussId);
		Russ russ = (Russ) russQuery.uniqueResult();
		currentSession.close();
		return russ;
	}

}
