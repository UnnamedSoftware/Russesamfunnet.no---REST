package no.ntnu.unnamedsoftware.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.unnamedsoftware.service.KnotService;

@RestController
public class KnotController {
	
	@Autowired
	private KnotService knotService;
	
	@RequestMapping(value="/knots", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getKnots(@RequestParam("russId") int theRussId){
		return knotService.getKnots(theRussId);
	}
	
	@RequestMapping(value="/completedKnots", produces=MediaType.APPLICATION_JSON_VALUE)
	public void getCompleted(@RequestParam("russId") int theRussId){
		knotService.getCompleted(theRussId);
	}
	
	@RequestMapping(value="/mapTest", produces=MediaType.APPLICATION_JSON_VALUE)
	public String mapTest(@RequestParam("russId") int theRussId){
		return knotService.mapTest(theRussId);
	}
	
	@RequestMapping(value="/getKnotsList", produces=MediaType.APPLICATION_JSON_VALUE)
	public String returnKnotsList(@RequestParam("russId") int theRussId){
		return knotService.getKnotsList(theRussId);
	}
	
	
}
