package no.ntnu.unnamedsoftware.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import no.ntnu.unnamedsoftware.service.KnotService;
@CrossOrigin
@RestController
public class KnotController {
	
	@Autowired
	private KnotService knotService;
	
	@RequestMapping(value="/knots", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getKnots(@RequestParam("russId") Long theRussId){
		return knotService.getKnots(theRussId);
	}
	
	@RequestMapping(value="/getKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getKnot(@RequestParam("knotId") Long theKnotId){
		return knotService.getKnot(theKnotId);
	}
	
	
	
	@RequestMapping(value="/completedKnots", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getCompleted(@RequestParam("russId") Long theRussId){
		return knotService.getCompleted(theRussId);
	}
	
	@RequestMapping(value="/getKnotsList", produces=MediaType.APPLICATION_JSON_VALUE)
	public String returnKnotsList(@RequestParam("russId") Long theRussId){
		return knotService.getKnotsList(theRussId);
	}
	
	@RequestMapping(value="/registerCompletedKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String registerCompletedKnot(@RequestParam("russId") Long theRussId, 
										@RequestParam("knotId") Long theKnotId,
										@RequestParam("witness1") Long witness1,
										@RequestParam("witness2") Long witness2){
		String registered = null;
		registered = knotService.registerCompletedKnot(theRussId, theKnotId, witness1, witness2);

		return registered;
	}
	
	@RequestMapping(value="/updateKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String updateKnot(@RequestParam("russId") Long theRussId){
		return "Status: OK";  //knotService.getKnotsList(theRussId);
	}
	
	@RequestMapping(value="/registerWitnessCompletedKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String registerCompletedKnot(@RequestParam("completedId") int theCompletedKnotId,
										@RequestParam("witness") Long witness){
		String registered = null;
		registered = knotService.registerWitnessCompletedKnot(theCompletedKnotId, witness);

		return registered;
	}
}
