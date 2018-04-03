package no.ntnu.unnamedsoftware.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.DAO.ErrorDAO;
import no.ntnu.unnamedsoftware.DAO.RussDAO;
import no.ntnu.unnamedsoftware.entity.Russ;

@Service
public class ErrorService {

	@Autowired
	ErrorDAO errorDAO;
	
	@Autowired
	RussDAO russDAO;
	
	@Autowired
	ObjectMapper mapper;
	
	public String createErrorReport(Long russId, String subject, String message)
	{
		Russ russ = russDAO.getUserRussFromId(russId);
		return writeAsJsonString(errorDAO.createErrorReport(russ, subject, message));
	}
	
	private String writeAsJsonString(Object object) {
		String feedInJsonString = null;
		try {
			feedInJsonString = mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return feedInJsonString;
	}
}
