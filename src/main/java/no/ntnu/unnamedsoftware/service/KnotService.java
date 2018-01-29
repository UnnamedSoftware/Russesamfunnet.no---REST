package no.ntnu.unnamedsoftware.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.DAO.KnotDAO;
import no.ntnu.unnamedsoftware.entity.Completed;
import no.ntnu.unnamedsoftware.entity.KnotTemp;
import no.ntnu.unnamedsoftware.entity.Knots;
import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.School;

@Service
public class KnotService {
	
	@Autowired
	KnotDAO knotDAO;
	
	@Autowired
	ObjectMapper mapper;
	
	public String getKnots(int theRussId)
	{
		int theSchoolId = knotDAO.getSchoolId(theRussId);
		return knotDAO.getKnots(theSchoolId);
	}
	
	public String getCompleted(int theRussId) {
		String completedInJsonString = null;
		List<Completed> completed = knotDAO.getCompleted(theRussId);
		try {
			completedInJsonString = mapper.writeValueAsString(completed);
		} catch (JsonGenerationException e) {e.printStackTrace();} 
		  catch (JsonMappingException e) { e.printStackTrace();} 
		  catch (IOException e) { e.printStackTrace();}
		return completedInJsonString;
	}
	
	
	public String getKnotsList(int theRussId) {
		String mapStringJson = null;
		//Liste over alle knutene denne brukeren har gjort
		List<Completed> completedList = null;
		//Liste over alle knutene i listen (offisielle + skolen til brukeren)
		List<Knots> allKnots = null;
		//Listen som skal gjøres om til JSON
		List<KnotTemp> listToBeReturned = new ArrayList<KnotTemp>();
		//Knutene fra denne skolen skal hentes
		int theSchoolId = knotDAO.getSchoolId(theRussId);
		allKnots = knotDAO.getKnotsList(theSchoolId);
		completedList = knotDAO.getCompletedKnots(theRussId);
		for(Knots k : allKnots) {
			boolean match = false;
			KnotTemp knotObj;
			for(Completed c : completedList) {
				//System.out.println("knot: " + k.getKnotId() + "     completed: " + c.getKnotId().getKnotId());
				if(k.getKnotId().equals(c.getKnotId().getKnotId())) {
					//System.out.println("It's a match!");
					match = true;
				}
				else {
					//System.out.println("It's not a match!");
				}
			}
			if(match) {
				// KnotTemp object med completed "true"
				listToBeReturned.add(new KnotTemp(k.getKnotId(), k.getDetails(), theSchoolId, true, theRussId));
			} else {
				// KnotTemp object med completed "false"
				listToBeReturned.add(new KnotTemp(k.getKnotId(), k.getDetails(), theSchoolId, false, theRussId));
			}
		}
		try {
			mapStringJson = mapper.writeValueAsString(listToBeReturned);
		} catch (JsonGenerationException e) {e.printStackTrace();} 
		  catch (JsonMappingException e) { e.printStackTrace();} 
		  catch (IOException e) { e.printStackTrace();}
		return mapStringJson;
	}

	public String registerCompletedKnot(int theRussId, int theKnotId, int witness1, int witness2) {
		//Dersom det ikke er vitner lag tomme vitneobjekter som kan fylles ut senere
		//dersom brukeren ønsker det
		Russ theWitness1 = null;
		Russ theWitness2 = null;
		
		//Hente russ object
		Russ theRuss = knotDAO.getRuss(theRussId);
		//Hente knute object
		Knots theKnot = knotDAO.getKnot(theKnotId);
		//sjekke vitner
		if(witness1 == 0) {
			// vitne1 er ikke lagt til
			theWitness1 = knotDAO.getEmptyWitness();
		}else{
			theWitness1 = knotDAO.getRuss(witness1);
		}
		if(witness2 == 0) {
			// vitne1 er ikke lagt til
			theWitness2 = knotDAO.getEmptyWitness();
		}else{
			theWitness2 = knotDAO.getRuss(witness2);
		}
		
		//legge alt inn i databasen
		
		return knotDAO.registerCompletedKnot(theRuss, theKnot, theWitness1, theWitness2); //knotDAO.registerCompletedKnot(theRussId, theKnotId);
	}

	public String registerWitnessCompletedKnot(int theCompletedKnotId, int witness) {
		
		
		//Completed theCompletedKnot = knotDAO.getCompletedKnot(theCompletedKnotId);
		Russ theWitness = knotDAO.getRuss(witness);
		
		try{
			//Russ witness1 = theCompletedKnot.getWitnessId1();
			//Russ witness2 = theCompletedKnot.getWitnessId2();
			return knotDAO.registerWitnessCompletedKnot(theCompletedKnotId, theWitness);
			/*
			if(witness1 == null) {
				return knotDAO.registerWitness1CompletedKnot(theCompletedKnot, theWitness);
			}if(witness2 == null) {
				return knotDAO.registerWitness2CompletedKnot(theCompletedKnot, theWitness);
			}else {
				return "Witness already registered for this knot";
			}*/
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "Something went wrong";
	}
	
	/* IDE!
	 * Hente en liste med knuter basert
	 * på om skolen ønsker å bruke
	 * den offisielle listen, eller en
	 * egendefinert liste
	 * eller begge listene
	 * 
	 * for å gjøre dette må vi ha
	 * en bruker_ID som inn parameter 
	 * også finne skoleinfo ut fra den ID'en
	 * */
}
