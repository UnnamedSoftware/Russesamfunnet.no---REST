package no.ntnu.unnamedsoftware.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.unnamedsoftware.service.AccessTokenDecrypterAndParser;
import no.ntnu.unnamedsoftware.service.RussService;
import no.ntnu.unnamedsoftware.service.ScoreboardService;

@CrossOrigin
@RestController
public class ScoreboardController {

	@Autowired
	private ScoreboardService scoreboardService;

	@Autowired
	private AccessTokenDecrypterAndParser tokenParser;

	@RequestMapping(value = "/scoreboardTop10Token", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional

	public String getScoreboardTop10Token(@RequestParam String accessToken) {
		Long theRussId = tokenParser.getRussId(accessToken);
		return scoreboardService.getScoreboardTop10(theRussId);
	}

	@RequestMapping(value = "/schoolScoreboardToken", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getSchoolScoreboardToken(@RequestParam String accessToken) {
		Long theRussId = tokenParser.getRussId(accessToken);
		return scoreboardService.getSchoolScoreboard(theRussId);
	}

	@RequestMapping(value = "/scoreboardTop10FacebookToken", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional

	public String getScoreboardTop10FacebookToken(@RequestParam String accessToken) {
		Long theRussId = tokenParser.decryptFacebookToken(accessToken);
		return scoreboardService.getScoreboardTop10(theRussId);
	}

	@RequestMapping(value = "/schoolScoreboardFacebookToken", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getSchoolScoreboardFacebookToken(@RequestParam String accessToken) {
		Long theRussId = tokenParser.decryptFacebookToken(accessToken);
		return scoreboardService.getSchoolScoreboard(theRussId);
	}

	@RequestMapping(value = "/scoreboardTop10", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional

	public String getScoreboardTop10(@RequestParam String accessToken, @RequestParam String type) {
		Long theRussId = null;;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}
		return scoreboardService.getScoreboardTop10(theRussId);
	}

	@RequestMapping(value = "/schoolScoreboard", produces = MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	public String getSchoolScoreboard(@RequestParam String accessToken, @RequestParam String type) {
		Long theRussId = null;;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}
		return scoreboardService.getSchoolScoreboard(theRussId);
	}

}
