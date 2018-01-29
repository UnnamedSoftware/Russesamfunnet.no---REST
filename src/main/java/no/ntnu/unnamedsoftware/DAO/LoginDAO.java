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
	public String getPassword(String email) {
		Session currentSession = sessionFactory.openSession();
		String passwordInJsonString = null;
		Query russQuery = currentSession.createQuery("from Russ r where (r.email = :email)")
				.setParameter("email", email);
	    Russ russ = (Russ) russQuery.uniqueResult();
		String password = russ.getRussPassword();
		return password;
	}

}
