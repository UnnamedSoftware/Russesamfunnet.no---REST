package no.ntnu.unnamedsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import no.ntnu.unnamedsoftware.service.ErrorService;

@Controller
public class ErrorController {
	
	@Autowired
	ErrorService errorService;
	

}
