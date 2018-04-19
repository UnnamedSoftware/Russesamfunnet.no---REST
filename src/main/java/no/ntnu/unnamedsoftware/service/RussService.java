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
import no.ntnu.unnamedsoftware.entity.Response;
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
		Russ russ = russDAO.getUserRussFromId(theRussId);
		
		return this.writeObjectAsJsonString(russ);
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
		if (user.getRussRole().equals("admin") || user.getRussRole().equals("system administrator")) {
			System.out.println("User is an admin!");
			return russDAO.confirmRuss(Long.valueOf(russToConfirm));

		}
		return null;
	}

	public String toggleAdmin(Long theRussId, Long russToMakeAdmin) {
		Russ user = russDAO.getUserRussFromId(theRussId);
		Russ russToAdmin = russDAO.getUserRussFromId(russToMakeAdmin);
		if (user.getRussRole().equals("admin") || user.getRussRole().equals("system administrator")) {
			if(russToAdmin.getRussRole().equals("russ"))
			{
				return writeObjectAsJsonString(russDAO.registerAdmin(russToMakeAdmin));
			} else if(russToAdmin.getRussRole().equals("admin"))
			{
				return writeObjectAsJsonString(russDAO.removeAdmin(russToMakeAdmin));
			}
		}
		return writeObjectAsJsonString(russToAdmin);
	}

	public String toggleRussConfirmation(Long russId, Long russToConfirm) {
		Russ user = russDAO.getUserRussFromId(russId);
		if (user.getRussRole().equals("admin") || user.getRussRole().equals("system administrator")) {
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
		if (user.getRussRole().equals("admin") || user.getRussRole().equals("system administrator")) {
			return russDAO.deleteUser(russToDelete);
		}
		return "You do not have permission to execute this command.";
	}
	
	public String searchForRuss(Long russId, String parameters)
	{
		if(russId != null)
		{
			return writeAsJsonString(russDAO.searchForRuss(russId, parameters));
		}
		return "You need to be logged to an official Russesamfunnet site.";
	}
	
	public String searchForRussByName(Long russId, String parameters)
	{
		if(russId != null)
		{
			return writeAsJsonString(russDAO.searchForRussByName(russId, parameters));
		}
		return "You need to be logged to an official Russesamfunnet site.";
	}
	
	public String searchForRussByNameOutsideSchool(Long russId, String parameters)
	{
		if(russId != null)
		{
			List<Russ> russ = russDAO.searchForRussByNameOutsideSchool(russId, parameters);
			
			List<Russ> russ100 = new ArrayList<>();
			for(int i = 0; i <= 100 ; i++)
			{
				try {
					russ100.add(russ.get(i));
				} catch (Exception e)
				{
					return writeAsJsonString(russ100);
				}
			}
			
			return writeAsJsonString(russ100);
		}
		return "You need to be logged to an official Russesamfunnet site.";
	}
	
	public String setProfilePicture(Long russId, String pictureName)
	{
		return writeObjectAsJsonString(new Response(russDAO.setProfilePicture(russId, pictureName)));
	}
	

}
