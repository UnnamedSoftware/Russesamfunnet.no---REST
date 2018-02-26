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

import no.ntnu.unnamedsoftware.DAO.SchoolDAO;
import no.ntnu.unnamedsoftware.entity.School;
@Service
public class SchoolService {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	SchoolDAO schoolDAO;
	
	public String getAllSchools()
	{
	

	return writeAsJsonString(schoolDAO.getAllSchools());
	
	}
	
	public String getMunicipalitySchools(String municipality)
	{
		return writeAsJsonString(schoolDAO.getMunicipalitySchools(municipality));
	
	}
	
	public String getLocationSchools(String location)
	{
		return writeAsJsonString(schoolDAO.getLocationSchools(location));
	
	}
		
	public String writeAsJsonString(Object object)
	{
		String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(object);
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
