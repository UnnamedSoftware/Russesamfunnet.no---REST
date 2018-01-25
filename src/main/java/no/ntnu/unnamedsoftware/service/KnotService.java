package no.ntnu.unnamedsoftware.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public void getCompleted(int theRussId) {
		knotDAO.getCompleted(theRussId);
	}
	
	public String mapTest(int theRussId) {
		String mapStringJson = null;
		int theSchoolId = knotDAO.getSchoolId(theRussId);
		String s1 = knotDAO.getKnots(theSchoolId);
		String s2 = knotDAO.getKnots(theSchoolId);
		Map newMap = new HashMap<String, String>();
		newMap.put(s2, "NotCompleted");
		newMap.put(s1, "Completed");
		try {
			mapStringJson = mapper.writeValueAsString(newMap);
		} catch (JsonGenerationException e) {e.printStackTrace();} 
		  catch (JsonMappingException e) { e.printStackTrace();} 
		  catch (IOException e) { e.printStackTrace();}
		return mapStringJson;
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
