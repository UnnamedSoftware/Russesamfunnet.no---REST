package no.ntnu.unnamedsoftware.DAO;

import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import no.ntnu.unnamedsoftware.entity.School;

@Repository
public class SchoolDAO {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public List<School> getAllSchools()
	{
		List<School> schoolList = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query theQuery = currentSession.createQuery("from School s"); 	
			schoolList = theQuery.list();
			return schoolList;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return schoolList;
	}
	
	@Transactional
	public List<School> getMunicipalitySchools(String municipality)
	{
		List<School> schoolList = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query theQuery = currentSession.createQuery("from School s where s.schoolMunicipality = :municipality")
					.setParameter("municipality", municipality);	
			schoolList = theQuery.list();
			return schoolList;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return schoolList;
	}
	
	@Transactional
	public List<School> getLocationSchools(String location)
	{
		List<School> schoolList = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query theQuery = currentSession.createQuery("from School s where s.schoolLocation = :location")
				.setParameter("location", location);		
			schoolList = theQuery.list();
			return schoolList;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return schoolList;
	}

}
