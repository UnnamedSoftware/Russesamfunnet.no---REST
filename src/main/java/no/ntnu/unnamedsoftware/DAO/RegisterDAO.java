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
	@Transactional
	public String registerUser(String userId, Long schoolId, String firstName, String lastName)
	{
		System.out.println("Success");
		School school = this.getSchool(schoolId);
		if(school == null)
		{
			return "There is no school with that name";
		}
		Session currentSession = sessionFactory.openSession();
		Russ russ = new Russ();
	//	russ.setRussId(new Long(userId));
		System.out.println(russ.getRussId());
		russ.setSchoolId(school);
		russ.setFirstName(firstName);
		russ.setLastName(lastName);
		russ.setRussRole("Russ");
		russ.setRussStatus("false");
		
		currentSession.save(russ);
	//	currentSession.getTransaction().commit();
		currentSession.close();
				
		
		return "User successfully registered";
	}
	
	@Transactional
	public String registerUserFB(String userId, Long schoolId, String firstName, String lastName)
	{
		
		
		System.out.println("Success");
		
		Long userIdOnFacebook = Long.parseLong(userId);
		
		School school = this.getSchool(schoolId);
		if(school == null)
		{
			return "There is no school with that name";
		}
		Session currentSession = sessionFactory.openSession();
		Russ russ = new Russ();
	//	russ.setRussId(new Long(userId));
		System.out.println(russ.getRussId());
		russ.setRussIdAlt(userIdOnFacebook);
		russ.setSchoolId(school);
		russ.setFirstName(firstName);
		russ.setLastName(lastName);
		russ.setRussRole("Russ");
		russ.setRussStatus("false");
		
		currentSession.save(russ);
	//	currentSession.getTransaction().commit();
		currentSession.close();
				
		
		return "User successfully registered";
	}
	
		@Transactional
		public School getSchool(Long schoolId) {
			Session currentSession = sessionFactory.openSession();
			School school = null;
			try{
				Query schoolQuery = currentSession.createQuery("from School s where s.schoolId = :schoolId")
						.setParameter("schoolId", schoolId);
				school = (School) schoolQuery.uniqueResult();
				System.out.println(school.getSchoolName());
			} catch(Exception e) {
				e.printStackTrace();
			}
				currentSession.close();
				return school;
			
		}
	}


