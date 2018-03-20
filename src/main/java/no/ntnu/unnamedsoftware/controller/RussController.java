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
	
	@RequestMapping(value="/userRuss", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getUserRuss(@RequestParam String accessToken, @RequestParam String type) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return russService.getUserRuss(theRussId);
	}
	
	@RequestMapping(value="/getAllRussAtSchool", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getAllRussAtSchool(@RequestParam String accessToken, @RequestParam String type) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
			System.out.println("facebook " + theRussId);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
			System.out.println("russesamfunnet " + theRussId);
		}	
		return russService.getAllRussAtSchool(theRussId);
	}
	
	@RequestMapping(value="/getAllRussAtSchoolStatusFalse", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getAllRussAtSchoolStatusFalse(@RequestParam String accessToken, @RequestParam String type) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
			System.out.println("facebook " + theRussId);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
			System.out.println("russesamfunnet " + theRussId);
		}	
		return russService.getAllRussAtSchoolStatusFalse(theRussId);
	}
	
	@RequestMapping(value="/confirmRuss", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String confirmRuss(@RequestParam String accessToken, 
					          @RequestParam String type,
					          @RequestParam String russToConfirm) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
			System.out.println("facebook " + theRussId);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
			System.out.println("russesamfunnet " + theRussId);
		}	
		return russService.confirmRuss(theRussId, russToConfirm);
	}
	
	@RequestMapping(value="/getOtherRuss", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getOtherRuss(@RequestParam String accessToken, @RequestParam String type, @RequestParam Long russId) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		if(theRussId != null)
		{
			return russService.getUserRuss(russId);
		} else
		{
			return null;
		}
	}
	
	@RequestMapping(value="/registerAdmin", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String registerAdmin(@RequestParam String accessToken, 
					          @RequestParam String type,
					          @RequestParam String russToMakeAdmin) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return russService.registerAdmin(theRussId, Long.valueOf(russToMakeAdmin));
	}
	
	
	/**
	 * Changes the "confirmed" value of a russ, either from false to true, or true to false.
	 * 
	 * @param accessToken
	 * @param type
	 * @param russToConfirm
	 * @return
	 */
	@RequestMapping(value="/toggleRussConfirmation", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String toggleRussConfirmation(@RequestParam String accessToken, @RequestParam String type, @RequestParam String russToConfirm)
	{
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return russService.toggleRussConfirmation(theRussId, Long.valueOf(russToConfirm));
	}
	
	@RequestMapping(value="/deleteUser", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String deleteUser(@RequestParam String accessToken, @RequestParam String type, @RequestParam String russToDelete)
	{
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return russService.deleteUser(theRussId, Long.valueOf(russToDelete));
	}
	
	@RequestMapping(value="/searchForRuss", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String searchForRuss(@RequestParam String accessToken, @RequestParam String type, @RequestParam String parameter)
	{
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return russService.searchForRuss(theRussId, parameter);
	}
	
    
}

