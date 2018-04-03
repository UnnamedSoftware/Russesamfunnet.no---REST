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

import no.ntnu.unnamedsoftware.service.AccessTokenDecrypterAndParser;
import no.ntnu.unnamedsoftware.service.KnotService;
@CrossOrigin
@RestController
public class KnotController {
	
	@Autowired
	private KnotService knotService;
	
	@Autowired
	private AccessTokenDecrypterAndParser tokenParser;
	
	
	@RequestMapping(value="/knotsToken", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getKnotsToken(@RequestParam String accessToken){
		Long theRussId = tokenParser.getRussId(accessToken);
		return knotService.getKnots(theRussId);
	}
	
	
	@RequestMapping(value="/getKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getKnot(@RequestParam("knotId") Long theKnotId){
		return knotService.getKnot(theKnotId);
	}
	
	

	@RequestMapping(value="/getKnotsListToken", produces=MediaType.APPLICATION_JSON_VALUE)
	public String returnKnotsListToken(@RequestParam String accessToken){
		Long theRussId = tokenParser.getRussId(accessToken);
		return knotService.getKnotsList(theRussId);
	}
	

	@RequestMapping(value="/registerCompletedKnotToken", produces=MediaType.APPLICATION_JSON_VALUE)
	public String registerCompletedKnotToken(@RequestParam String accessToken, 
										@RequestParam("knotId") Long theKnotId,
										@RequestParam("witness1") Long witness1,
										@RequestParam("witness2") Long witness2){
		String registered = null;
		Long theRussId = tokenParser.getRussId(accessToken);
		registered = knotService.registerCompletedKnot(theRussId, theKnotId, witness1, witness2);

		return registered;
	}
	
	@RequestMapping(value="/registerWitnessCompletedKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String registerCompletedKnot(@RequestParam("completedId") int theCompletedKnotId,
										@RequestParam("witness") Long witness){
		String registered = null;
		registered = knotService.registerWitnessCompletedKnot(theCompletedKnotId, witness);

		return registered;
	}
	
	@RequestMapping(value="/knotsFacebookToken", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getKnotsFacebookToken(@RequestParam String accessToken){
		Long theRussId = tokenParser.decryptFacebookToken(accessToken);
		return knotService.getKnots(theRussId);
	}
	
	@RequestMapping(value="/completedKnotsToken", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getCompletedToken(@RequestParam String accessToken){
		Long theRussId = tokenParser.getRussId(accessToken);
		return knotService.getCompleted(theRussId);
	}
	
	
	@RequestMapping(value="/completedKnotsFacebookToken", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getCompletedFacebookToken(@RequestParam String accessToken){
		Long theRussId = tokenParser.decryptFacebookToken(accessToken);
		return knotService.getCompleted(theRussId);
	}
	
	@RequestMapping(value="/getKnotsListFacebookToken", produces=MediaType.APPLICATION_JSON_VALUE)
	public String returnKnotsListFacebookToken(@RequestParam String accessToken){
		Long theRussId = tokenParser.decryptFacebookToken(accessToken);
		return knotService.getKnotsList(theRussId);
	}
	
	
	@RequestMapping(value="/registerCompletedKnotFacebookToken", produces=MediaType.APPLICATION_JSON_VALUE)
	public String registerCompletedKnotFacebookToken(@RequestParam String accessToken, 
										@RequestParam("knotId") Long theKnotId,
										@RequestParam("witness1") Long witness1,
										@RequestParam("witness2") Long witness2){
		String registered = null;
		Long theRussId = tokenParser.decryptFacebookToken(accessToken);
		registered = knotService.registerCompletedKnot(theRussId, theKnotId, witness1, witness2);

		return registered;
	}
	

	
	@RequestMapping(value="/addKnotFacebook", produces=MediaType.APPLICATION_JSON_VALUE)
	public String addKnotFacebook(@RequestParam("accessToken") String accessToken,@RequestParam("knotName") String knotName,@RequestParam("knotDescription") String knotDescription)
	{
		Long russId = tokenParser.decryptFacebookToken(accessToken);
		return knotService.addKnot(russId, knotName, knotDescription);
	}
	

	
	@RequestMapping(value="/deleteKnotFacebook", produces=MediaType.APPLICATION_JSON_VALUE)
	public String deleteKnotFacebook(@RequestParam("accessToken") String accessToken,@RequestParam("knotId") Long knotId)
	{
		Long russId = tokenParser.decryptFacebookToken(accessToken);
		return knotService.deleteKnot(russId, knotId);
	}

	
	
	
	
	
	@RequestMapping(value="/knots", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getKnots(@RequestParam String accessToken, @RequestParam String type){
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return knotService.getKnots(theRussId);
	}
	
	@RequestMapping(value="/getKnotsList", produces=MediaType.APPLICATION_JSON_VALUE)
	public String returnKnotsList(@RequestParam String accessToken, @RequestParam String type){
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return knotService.getKnotsList(theRussId);
	}
	
	@RequestMapping(value="/registerCompletedKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String registerCompletedKnot(@RequestParam String accessToken,
										@RequestParam("type") String type,  
										@RequestParam("knotId") Long theKnotId,
										@RequestParam("witness1") Long witness1,
										@RequestParam("witness2") Long witness2){
		
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return knotService.registerCompletedKnot(theRussId, theKnotId, witness1, witness2);
	}
	
	@RequestMapping(value="/unRegisterCompletedKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String unRegisterCompletedKnot(@RequestParam String accessToken,	@RequestParam("type") String type, @RequestParam("knotId") Long theKnotId){
		
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return knotService.unRegisterCompletedKnot(theRussId, theKnotId);
	}
	
	@RequestMapping(value="/completedKnots", produces=MediaType.APPLICATION_JSON_VALUE)
	public String getCompleted(@RequestParam String accessToken, @RequestParam String type){
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return knotService.getCompleted(theRussId);
	}
	
	@RequestMapping(value="/addKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String addKnot(@RequestParam("accessToken") String accessToken, @RequestParam String type, @RequestParam("knotName") String knotName,@RequestParam("knotDescription") String knotDescription)
	{
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return knotService.addKnot(theRussId, knotName, knotDescription);
	}
	
	@RequestMapping(value="/deleteKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String deleteKnot(@RequestParam("accessToken") String accessToken, @RequestParam String type,@RequestParam("knotId") Long knotId)
	{
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return knotService.deleteKnot(theRussId, knotId);
	}
	
	@RequestMapping(value="/updateKnot", produces=MediaType.APPLICATION_JSON_VALUE)
	public String updateKnot(@RequestParam("accessToken") String accessToken, @RequestParam String type, @RequestParam Long knotId, @RequestParam("knotName") String knotName,@RequestParam("knotDescription") String knotDescription)
	{
		Long theRussId = null;
		if (type.equals("facebook")) {
			theRussId = tokenParser.decryptFacebookToken(accessToken);
		}else if(type.equals("russesamfunnet"))
		{
			theRussId = tokenParser.getRussId(accessToken);
		}	
		return knotService.updateKnot(theRussId, knotId, knotName, knotDescription);
	}
	
	
}
