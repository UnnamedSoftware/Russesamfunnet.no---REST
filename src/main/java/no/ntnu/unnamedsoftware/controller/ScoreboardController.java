package no.ntnu.unnamedsoftware.controller;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.unnamedsoftware.service.RussService;
import no.ntnu.unnamedsoftware.service.ScoreboardService;

@RestController
public class ScoreboardController {
	
	@Autowired
	private ScoreboardService scoreboardService;
	
	
	@RequestMapping(value="/scoreboard", produces=MediaType.APPLICATION_JSON_VALUE)
	@Transactional
	
	public String getScoreboard() {
		
		return scoreboardService.getScoreboard();
	}

}
