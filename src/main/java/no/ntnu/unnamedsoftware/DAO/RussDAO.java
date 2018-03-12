package no.ntnu.unnamedsoftware.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import no.ntnu.unnamedsoftware.entity.Feed;
import no.ntnu.unnamedsoftware.entity.Russ;



@Repository
public class RussDAO {
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public List<Russ> getRuss()
	{
		List<Russ> userInfo = null;
		
		try(Session currentSession = sessionFactory.openSession()){
			Query theQuery = currentSession.
					createQuery("from Russ r"); 
			
			userInfo = theQuery.list();;
			//currentSession.close();
			return userInfo;
			//return JSON
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	
	@Transactional
	public Russ getUserRuss(Long theRussId)
	{
		Russ russ = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query russQuery = currentSession.createQuery("from Russ r where (r.russId = :russId)")
					.setParameter("russId", theRussId);
			russ = (Russ) russQuery.uniqueResult();
			//currentSession.close();
			return russ;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return russ;
	}

}
