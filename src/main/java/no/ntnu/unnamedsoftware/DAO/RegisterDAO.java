package no.ntnu.unnamedsoftware.DAO;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.School;

@Repository
public class RegisterDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	SchoolDAO schoolDAO;
	
	@Transactional
	public String registerUser(String userId, Long schoolId, String firstName, String lastName)
	{
		
		School school = schoolDAO.getSchoolObjectFromId(schoolId);
		if(school == null)
		{
			return "There is no school with that name";
		}
		try(Session currentSession = sessionFactory.openSession()){
			Russ russ = new Russ();
			russ.setSchoolId(school);
			russ.setFirstName(firstName);
			russ.setLastName(lastName);
			russ.setRussRole("Russ");
			russ.setRussStatus("false");
			currentSession.save(russ);
			return "User successfully registered";
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "registerUser error";
	}
	
	@Transactional
	public String registerUserFB(String userId, Long schoolId, String firstName, String lastName)
	{
		Long userIdOnFacebook = Long.parseLong(userId);
		School school = schoolDAO.getSchoolObjectFromId(schoolId);
		if(school == null)
		{
			return "There is no school with that name";
		}
		try(Session currentSession = sessionFactory.openSession()){
			Russ russ = new Russ();
			russ.setRussIdAlt(userIdOnFacebook);
			russ.setSchoolId(school);
			russ.setFirstName(firstName);
			russ.setLastName(lastName);
			russ.setRussRole("Russ");
			russ.setRussStatus("false");
			currentSession.save(russ);
			return "User successfully registered";
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "registerUserFB error";
	}

	public String russasamfunnetRegister(String firstName, String lastName, String email, String password, String schoolIdS) {
		Long schoolId = Long.valueOf(schoolIdS);
		School school = schoolDAO.getSchoolObjectFromId(schoolId);
		if(school == null)
		{
			return "There is no school with that name";
		}
		try(Session currentSession = sessionFactory.openSession()){
			Russ russ = new Russ();
			russ.setSchoolId(school);
			russ.setFirstName(firstName);
			russ.setLastName(lastName);
			russ.setEmail(email);
			russ.setRussPassword(password);
			russ.setRussRole("Russ");
			russ.setRussStatus("false");
			currentSession.save(russ);
			return "User successfully registered";
		} catch(Exception e) {
			e.printStackTrace();
		}
		return "russesamfunnetRegister error";
	}
	
}

