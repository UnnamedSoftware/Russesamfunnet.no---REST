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
	public Boolean checkUser(String userId) {
		boolean dbUserId = false;
		try(Session currentSession = sessionFactory.openSession()){
			Query russQuery = currentSession.createQuery("from Russ r where (r.russId = :russId)")
					.setParameter("russId", userId);
		    Russ russ = (Russ) russQuery.uniqueResult();
		    if(russ != null) {
		    	dbUserId = true;
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dbUserId;
	}
	
	@Transactional
	public Boolean checkUserFB(String userId) {
		boolean dbUserId = false;
		try(Session currentSession = sessionFactory.openSession()) {
			Query russQuery = currentSession.createQuery("from Russ r where (r.russIdAlt = :userId)")
					.setParameter("userId", userId);
		    Russ russ = (Russ) russQuery.uniqueResult();
		    if(russ != null) {
		    	dbUserId = true;
		    }
		}catch(Exception e) {
			e.getStackTrace();
		}
		return dbUserId;
	}

}
