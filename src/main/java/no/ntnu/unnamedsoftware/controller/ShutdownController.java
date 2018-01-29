package no.ntnu.unnamedsoftware.controller;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShutdownController {
	
	@RequestMapping(value="/shutdown/{code}", produces=MediaType.APPLICATION_JSON_VALUE)
	public String registerCompletedKnot(@PathVariable("code") int code) {
		if(code == 54321) {
			System.exit(500);
		}else{
			return "Error: Invalid shutdown code";
		}
		return "Error: Something went wrong";
	}
}
