package no.ntnu.unnamedsoftware.service;

import java.io.IOException;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.Scoreboard;

@Service
public class ScoreboardService {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ObjectMapper mapper;
	
	public String getScoreboard(int theRussId)
	{
	String jsonInString = null;
	Session currentSession = sessionFactory.openSession();
	
	Query theQuery = currentSession.
			createQuery("from Scoreboard s");  
	
	List<Scoreboard> userInfo = theQuery.list();
	System.out.println(userInfo.size());
	Scoreboard r = userInfo.get(0);
	System.out.println("" + r.getRussId() + r.getPoints());
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
