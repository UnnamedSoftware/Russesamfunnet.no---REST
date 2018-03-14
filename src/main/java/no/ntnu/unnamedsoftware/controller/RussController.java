package no.ntnu.unnamedsoftware.controller;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.unnamedsoftware.service.AccessTokenDecrypterAndParser;
import no.ntnu.unnamedsoftware.service.RussService;


@CrossOrigin
@RestController
public class RussController 
{
	@Autowired
	private RussService russService;
	
	@Autowired
	private AccessTokenDecrypterAndParser tokenParser;
	
	
	@RequestMapping(value="/russ", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	
	public String getRuss() {
		
		return russService.getRuss();
	}
	
	@RequestMapping(value="/userRuss", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getUserRuss(@RequestParam Long russId) {
		
		return russService.getUserRuss(russId);
	}
	
	@RequestMapping(value="/userRussToken", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getUserRussToken(@RequestParam String accessToken) {
		Long russId = tokenParser.getRussId(accessToken);		
		return russService.getUserRuss(russId);
	}
	
	@RequestMapping(value="/userRussFacebookToken", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getUserRussFacebookToken(@RequestParam String accessToken) {
		Long russId = tokenParser.decryptFacebookToken(accessToken);		
		return russService.getUserRuss(russId);
	}
    
}

