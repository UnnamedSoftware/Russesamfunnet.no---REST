package no.ntnu.unnamedsoftware.service;

import java.util.List;
import java.io.IOException;
import java.util.ArrayList;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.DAO.RussDAO;
import no.ntnu.unnamedsoftware.entity.Russ;
import no.ntnu.unnamedsoftware.entity.School;

@Service
public class RussService {

	@Autowired
	RussDAO russDAO;

	@Autowired
	ObjectMapper mapper;

	public String getRuss() {
		List<Russ> russ = russDAO.getRuss();
		return this.writeAsJsonString(russ);

	}

	public String getUserRuss(Long theRussId) {
		List<Russ> russ = new ArrayList<Russ>();
		russ.add(russDAO.getUserRussFromId(theRussId));
		return this.writeAsJsonString(russ);
	}

	public String getAllRussAtSchool(Long theRussId) {

		// 1 - get user
		Russ user = russDAO.getUserRussFromId(theRussId);// .getSchoolId().getSchoolId();
		System.out.println("User " + user.toString());
		// 2 - get school id
		Long schoolId = user.getSchoolId().getSchoolId();
		System.out.println("SchoolID " + schoolId);
		// 3 - get all russ at the school
		List<Russ> russAtSchool = russDAO.getAllRussAtSchool(schoolId);
		System.out.println("List " + russAtSchool.size());

		System.out.println("JSON  " + this.writeAsJsonString(russAtSchool));
		return this.writeAsJsonString(russAtSchool);
	}

	private String writeAsJsonString(List<Russ> object) {
		String objectInJsonString = null;
		try {
			objectInJsonString = mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objectInJsonString;
	}

	private String writeObjectAsJsonString(Object object) {
		String objectInJsonString = null;
		try {
			objectInJsonString = mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objectInJsonString;
	}

	public String getAllRussAtSchoolStatusFalse(Long theRussId) {
		// 1 - get user
		Russ user = russDAO.getUserRussFromId(theRussId);// .getSchoolId().getSchoolId();
		System.out.println("User " + user.toString());
		// 2 - get school id
		Long schoolId = user.getSchoolId().getSchoolId();
		System.out.println("SchoolID " + schoolId);
		// 3 - get all russ at the school
		List<Russ> russAtSchool = russDAO.getAllRussAtSchoolStatusFalse(schoolId);
		System.out.println("List " + russAtSchool.size());

		System.out.println("JSON  " + this.writeAsJsonString(russAtSchool));
		return this.writeAsJsonString(russAtSchool);
	}

	public String confirmRuss(Long theRussId, String russToConfirm) {
		Russ user = russDAO.getUserRussFromId(theRussId);
		if (user.getRussRole().equals("Admin") || user.getRussRole().equals("system administrator")) {
			System.out.println("User is an admin!");
			return russDAO.confirmRuss(Long.valueOf(russToConfirm));

		}
		return null;
	}

	public String registerAdmin(Long theRussId, Long russToMakeAdmin) {
		Russ user = russDAO.getUserRussFromId(theRussId);
		if (user.getRussRole().equals("Admin") || user.getRussRole().equals("system administrator")) {
			return writeObjectAsJsonString(russDAO.registerAdmin(russToMakeAdmin));
		}
		return null;
	}

	public String toggleRussConfirmation(Long russId, Long russToConfirm) {
		Russ user = russDAO.getUserRussFromId(russId);
		if (user.getRussRole().equals("Admin") || user.getRussRole().equals("system administrator")) {
			Russ russToBeConfirmed = russDAO.getUserRussFromId(russToConfirm);
			if (russToBeConfirmed.getRussStatus().equals("confirmed")) {
				return russDAO.unConfirmRuss(russToConfirm);
			} else if (russToBeConfirmed.getRussStatus().equals("false")) {
				return russDAO.confirmRuss(russToConfirm);
			}
		}
		return null;
	}
	
	public String deleteUser(Long russId, Long russToDelete)
	{
		Russ user = russDAO.getUserRussFromId(russId);
		if (user.getRussRole().equals("Admin") || user.getRussRole().equals("system administrator")) {
			return russDAO.deleteUser(russToDelete);
		}
		return "You do not have permission to execute this command.";
	}
	
	public String searchForRuss(Long russId, String parameters)
	{
		if(russId != null)
		{
			return writeAsJsonString(russDAO.searchForRuss(parameters));
		}
		return "You need to be logged to an official Russesamfunnet site.";
	}

}
