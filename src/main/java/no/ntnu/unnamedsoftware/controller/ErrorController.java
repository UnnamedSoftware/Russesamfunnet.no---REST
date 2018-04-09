package no.ntnu.unnamedsoftware.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import no.ntnu.unnamedsoftware.service.AccessTokenDecrypterAndParser;
import no.ntnu.unnamedsoftware.service.ErrorService;
@CrossOrigin
@Controller
public class ErrorController {
	
	@Autowired
	ErrorService errorService;
	
	@Autowired
	private AccessTokenDecrypterAndParser tokenParser;
	
	
	@RequestMapping(value="/createErrorReport", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String createErrorReport(@RequestParam String accessToken, @RequestParam String type, @RequestParam String subject, @RequestParam String message) {
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return errorService.createErrorReport(theRussId, subject, message);
		
	}

}
