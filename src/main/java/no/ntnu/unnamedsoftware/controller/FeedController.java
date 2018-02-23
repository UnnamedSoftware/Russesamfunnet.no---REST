package no.ntnu.unnamedsoftware.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import no.ntnu.unnamedsoftware.service.FeedService;
@CrossOrigin
@Controller
public class FeedController {
	
	@Autowired
	private FeedService feedService;
	

	@RequestMapping(value="/schoolFeed", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	@ResponseBody
	public String getfeed(@RequestParam("russId") Long theRussId) {
		return feedService.getSchoolFeed(theRussId);
		
	}

}
