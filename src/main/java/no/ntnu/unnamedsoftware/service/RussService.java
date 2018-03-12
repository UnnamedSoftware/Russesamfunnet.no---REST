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

import no.ntnu.unnamedsoftware.DAO.RussDAO;
import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.School;

@Service
public class RussService {
	
	@Autowired
	RussDAO russDAO;
	
	@Autowired
	ObjectMapper mapper;
	
	public String getRuss()
	{
		List<Russ> russ = russDAO.getRuss();
		return this.writeAsJsonString(russ);
		
	}
	
	public String getUserRuss(Long theRussId)
	{
		List<Russ> russ = new ArrayList<Russ>();
		russ.add(russDAO.getUserRussFromId(theRussId));
		return this.writeAsJsonString(russ);
	}
	
	private String writeAsJsonString(List<Russ> object) {
		String objectInJsonString = null;
		try {
			objectInJsonString = mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objectInJsonString;
	}
	

}
