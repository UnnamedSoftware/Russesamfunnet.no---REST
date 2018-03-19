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
import no.ntnu.unnamedsoftware.service.FeedService;
@CrossOrigin
@Controller
public class FeedController {
	
	@Autowired
	private FeedService feedService;
	
	@Autowired
	private AccessTokenDecrypterAndParser tokenParser;
	

	@RequestMapping(value="/schoolFeedToken", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String getfeedToken(@RequestParam String accessToken) {
		Long theRussId = tokenParser.getRussId(accessToken);
		return feedService.getSchoolFeed(theRussId);
		
	}
	
	@RequestMapping(value="/postFeedToSchoolToken", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String postfeedToken(@RequestParam String accessToken, @RequestParam String message) {
		Long theRussId = tokenParser.getRussId(accessToken);
		return feedService.postFeed(theRussId, message);
		
	}
	
	@RequestMapping(value="/schoolFeedFacebookToken", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String getfeedFacebookToken(@RequestParam String accessToken) {
		Long theRussId = tokenParser.decryptFacebookToken(accessToken);
		return feedService.getSchoolFeed(theRussId);
		
	}
	
	@RequestMapping(value="/postFeedToSchoolFacebookToken", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String postfeedFacebookToken(@RequestParam String accessToken, @RequestParam String message) {
		Long theRussId = tokenParser.decryptFacebookToken(accessToken);
		return feedService.postFeed(theRussId, message);
		
	}
	
	@RequestMapping(value="/postFeedToSchool", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String postfeed(@RequestParam String accessToken, @RequestParam String type, @RequestParam String message) {
		Long theRussId = null;;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return feedService.postFeed(theRussId, message);
		
	}
	
	@RequestMapping(value="/schoolFeed", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String getfeed(@RequestParam String accessToken, @RequestParam String type) {
		Long theRussId = null;;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return feedService.getSchoolFeed(theRussId);
		
	}


}
