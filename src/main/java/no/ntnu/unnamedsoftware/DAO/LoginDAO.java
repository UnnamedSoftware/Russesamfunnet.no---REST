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

import no.ntnu.unnamedsoftware.entity.Russ;

@Repository
public class LoginDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	

	
	@Transactional
	public Russ getRuss(String email) {
		Session currentSession = sessionFactory.openSession();
		//String passwordInJsonString = null;
		Russ russ = null; 
		try {
			Query russQuery = currentSession.createQuery("from Russ r where r.email =:email")
					.setParameter("email", email);
		    russ = (Russ) russQuery.uniqueResult();
			
		}catch(Exception e) {
			e.getStackTrace();
		}
		currentSession.close();
		return russ;
	}
	
	@Transactional
	public Boolean checkUser(String userId) {
		Session currentSession = sessionFactory.openSession();
		//String passwordInJsonString = null;
		boolean dbUserId = false;
		try {
			Query russQuery = currentSession.createQuery("from Russ r where (r.userId = :userId)")
					.setParameter("userId", userId);
		    Russ russ = (Russ) russQuery.uniqueResult();
		    if(russ != null) {
		    	dbUserId = true;
		    }
			
		}catch(Exception e) {
			e.getStackTrace();
		}
		currentSession.close();
		return dbUserId;
	}

}
