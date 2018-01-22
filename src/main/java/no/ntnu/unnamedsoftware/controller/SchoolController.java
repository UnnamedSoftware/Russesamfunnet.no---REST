package no.ntnu.unnamedsoftware.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.entity.*;

@RestController
public class SchoolController {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ObjectMapper mapper;
	
	@RequestMapping("/hello")
	public String sayHi(){
		
		return "Hi";
	}
	
	@RequestMapping("/users")
	public List<User> getUsers(){
		
		return Arrays.asList(
				new User("User1", "Email1", "Password1"),
				new User("User2", "Email2", "Password2"),
				new User("User3", "Email3", "Password3"));
	}
	
	@RequestMapping(value="/schools", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getSchools() {
		System.out.println("Hello?");
		String jsonInString = null;
		Session currentSession = sessionFactory.openSession();
		
		Query theQuery = currentSession.
				createQuery("from School s"); 
		
		List<School> userInfo = theQuery.list();
		System.out.println(userInfo.size());
		School s = userInfo.get(0);
		System.out.println(s.getSchoolName());
		User u = new User(s.getSchoolName(), s.getSchoolStatus(), s.getSchoolName());
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
	
	@RequestMapping(value="/russ", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getRuss() {
		
		return "string";
	}
	

}
