package no.ntnu.unnamedsoftware.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.xml.ws.Response;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.entity.LoginStatus;
import no.ntnu.unnamedsoftware.service.RegisterService;

@CrossOrigin
@RestController
public class RegisterController {
	
	@Autowired
	RegisterService registerService;
	
	@Autowired
	ObjectMapper mapper;
	
	@RequestMapping(value="/facebookRegister", produces=MediaType.APPLICATION_JSON_VALUE)
	public String facebookRegister(@RequestParam("accessToken") String accessToken, @RequestParam("birthdate") String birthdate, @RequestParam("schoolId") String schoolId) {     
        
		return registerService.facebookRegister(accessToken, birthdate, schoolId);
	}
}
