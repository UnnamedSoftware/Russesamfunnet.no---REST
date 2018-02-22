package no.ntnu.unnamedsoftware.DAO;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import no.ntnu.unnamedsoftware.entity.School;

@Repository
public class SchoolDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public List<School> getAllSchools()
	{
		System.out.println("Hello?");
		String jsonInString = null;
		Session currentSession = sessionFactory.openSession();
		
		Query theQuery = currentSession.
				createQuery("from School s"); 
		
		List<School> schoolList = theQuery.list();
		System.out.println(schoolList.size());
		School s = schoolList.get(0);
		System.out.println(s.getSchoolName());
		return schoolList;
	}
	
	public List<School> getSchools(String municipality, String location)
	{
		System.out.println("Hello?");
		System.out.println(municipality);
		System.out.println(location);
		String jsonInString = null;
		Session currentSession = sessionFactory.openSession();
		
		Query theQuery = currentSession.createQuery("from School s where s.schoolMunicipality = :municipality")
				.setParameter("municipality", municipality);
		
		List<School> schoolList = theQuery.list();
		System.out.println(schoolList.size());
		School s = schoolList.get(0);
		System.out.println(s.getSchoolName());
		return schoolList;
	}

}
