package no.ntnu.unnamedsoftware.controller;

import java.util.Optional;

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
	public String getCompleted(@RequestParam("russId") int theRussId){
		return knotService.getCompleted(theRussId);
	}
	
	@RequestMapping(value="/getKnotsList", produces=MediaType.APPLICATION_JSON_VALUE)
	public String returnKnotsList(@RequestParam("russId") int theRussId){
		return knotService.getKnotsList(theRussId);
	}
	
	@RequestMapping(value="/registerCompletedKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String registerCompletedKnot(@RequestParam("russId") int theRussId, 
										@RequestParam("knotId") int theKnotId,
										@RequestParam("witness1") int witness1,
										@RequestParam("witness2") int witness2){
		String registered = null;
		registered = knotService.registerCompletedKnot(theRussId, theKnotId, witness1, witness2);

		return registered;
	}
	
	@RequestMapping(value="/updateKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String updateKnot(@RequestParam("russId") int theRussId){
		return "Status: OK";  //knotService.getKnotsList(theRussId);
	}
	
	@RequestMapping(value="/registerWitnessCompletedKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String registerCompletedKnot(@RequestParam("completedId") int theCompletedKnotId,
										@RequestParam("witness") int witness){
		String registered = null;
		registered = knotService.registerWitnessCompletedKnot(theCompletedKnotId, witness);

		return registered;
	}
}
