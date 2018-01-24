package no.ntnu.unnamedsoftware.service;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.School;

@Service
public class RussService {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ObjectMapper mapper;
	
	public String getRuss()
	{
	String jsonInString = null;
	Session currentSession = sessionFactory.openSession();
	
	Query theQuery = currentSession.
			createQuery("from Russ s"); 
	
	List<Russ> userInfo = theQuery.list();
	System.out.println(userInfo.size());
	Russ r = userInfo.get(0);
	System.out.println(r.getFirstName() + r.getLastName());
	//return JSON
	
	//ObjectMapper mapper = new ObjectMapper();
	
	try {
		jsonInString = mapper.writeValueAsString(userInfo);
	} catch (JsonGenerationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JsonMappingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return jsonInString;
	
	}
	

}
