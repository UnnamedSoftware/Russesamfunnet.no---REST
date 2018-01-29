package no.ntnu.unnamedsoftware.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.DAO.LoginDAO;

@Service
public class LoginService {

	@Autowired
	LoginDAO loginDAO;

	@Autowired
	ObjectMapper mapper;

	public String Login(String email, String password) {
		String loginInJsonString = null;
		String loginSuccess = "false";

		if (password.equals(loginDAO.getPassword(email))) {
			loginSuccess = "true";
		}

		try {
			loginInJsonString = mapper.writeValueAsString(loginSuccess);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loginInJsonString;
	}
}
