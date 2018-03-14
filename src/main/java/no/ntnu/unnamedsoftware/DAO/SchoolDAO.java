package no.ntnu.unnamedsoftware.DAO;

import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import no.ntnu.unnamedsoftware.entity.Russ;
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
	
	@Transactional
	public School getSchoolObject(Long theRussId) {
		Russ russ = null;
		try(Session currentSession = sessionFactory.openSession()){
			Query russQuery = currentSession.createQuery("from Russ r where r.russId = :theRussId")
					.setParameter("theRussId", theRussId);
			russ = (Russ) russQuery.uniqueResult();
			if (russ == null)
			{
				//do something to prevent nullPointer
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return russ.getSchoolId();
	}
	
	@Transactional
	public School getSchoolObjectFromId(Long schoolId) {
		School school;
		try(Session currentSession = sessionFactory.openSession()){
			Query schoolQuery = currentSession.createQuery("from School s where s.schoolId = :schoolId")
					.setParameter("schoolId", schoolId);
			school = (School) schoolQuery.uniqueResult();
			return school;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	@Transactional
	public Long getSchoolId(Long theRussId) {
		Long errorCode = new Long(9001);
		try(Session currentSession = sessionFactory.openSession()){
			Query russQuery = currentSession.createQuery("from Russ r where r.russId = :theRussId")
					.setParameter("theRussId", theRussId);
			Russ test = (Russ) russQuery.uniqueResult();
			if(test != null) {
				return test.getSchoolId().getSchoolId();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return errorCode;
	}
	
	@Transactional
	public School getSchool(String schoolName) {
		Long errorCode = new Long(9001);
		try(Session currentSession = sessionFactory.openSession()){
			Query schoolQuery = currentSession.createQuery("from School s where s.schoolName = :schoolName")
					.setParameter("schoolName", schoolName);
			School school = (School) schoolQuery.uniqueResult();
			return school;
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

}
