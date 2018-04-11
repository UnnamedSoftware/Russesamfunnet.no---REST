package no.ntnu.unnamedsoftware.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.AgeRange;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.DAO.RegisterDAO;
import no.ntnu.unnamedsoftware.DAO.RussDAO;
import no.ntnu.unnamedsoftware.entity.LoginObjectToReturn;
import no.ntnu.unnamedsoftware.entity.LoginStatus;
import no.ntnu.unnamedsoftware.entity.Response;

import org.mindrot.jbcrypt.BCrypt;

@Service
public class RegisterService {
	
	private Facebook facebook;
    private ConnectionRepository connectionRepository;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	RegisterDAO registerDAO;
	
	@Autowired
	SchoolService schoolService;
	
	@Autowired
	RussDAO russDAO;
	
	@Autowired
	LoginService loginService;
	
	public String facebookRegister(String accessToken,String birthdate, String schoolName) {
			Long schoolId = schoolService.getSchool(schoolName);
			System.out.println(schoolId);
			LoginStatus loginStatus = new LoginStatus();
			String userId;
			String appToken = "291199641408779|P9GEtCoB6TjzkZjbeAPTbcC2CV4";
			String appID = "291199641408779";
			String app_id = null;
			String firstName = null;
			String lastName = null;
			String birthday = "";
			JSONObject jsonObject4 = null;
			int ageRange;
			

			JSONObject jsonObject = new JSONObject();
			try {
				String url = "https://graph.facebook.com/debug_token?input_token=" + accessToken + "&access_token="
						+ appToken;
				String JSONString = this.uRLConnectionReader(url);

				JSONObject jsonObj = new JSONObject(JSONString);
				JSONObject jsonObj2 = jsonObj.getJSONObject("data");
				userId = jsonObj2.getString("user_id");
				app_id = jsonObj2.getString("app_id");
				String newUrl = "https://graph.facebook.com/me?fields=id,first_name,last_name,age_range&access_token=" + accessToken;
				JSONObject jsonObj3 = new JSONObject(this.uRLConnectionReader(newUrl));
				firstName = jsonObj3.getString("first_name");
				lastName = jsonObj3.getString("last_name");
				if(birthdate != null)
				{
				birthday = birthdate;
				}else {
					//get birthday from facebook.
				}
				jsonObject4 = jsonObj3.getJSONObject("age_range");
				ageRange = jsonObject4.getInt("min");
				
				
				if (app_id.equals(appID) && ageRange >= 17) {
					loginStatus.setLoginStatus(registerDAO.registerUserFB(userId, schoolId, firstName, lastName));
					loginStatus.setUserId(Long.valueOf(userId));
					return getJsonString(loginStatus);
							
				} else {
					if(ageRange < 17)
					{
						loginStatus.setLoginStatus("User is younger than 17");
						return getJsonString(loginStatus);
					}else {
						
					System.out.println("Access token appId is not the same as application appId");
					System.out.println("Application appId");
					System.out.println(appID);
					System.out.println("Access token appId");
					System.out.println(app_id);
					loginStatus.setLoginStatus("Must log in through the facebook button in the app.");
					return getJsonString(loginStatus);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				loginStatus.setLoginStatus("Error: something went wrong.");
				return getJsonString(loginStatus);
		}
	}
	
	//Retieves the data from the given url string.
	public String uRLConnectionReader(String urlString) {
		try {
			System.out.println(urlString);
			HttpsURLConnection con = (HttpsURLConnection) new URL(urlString).openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.connect();
			InputStreamReader inputStream = new InputStreamReader(con.getInputStream());
			BufferedReader reader = new BufferedReader(inputStream);
			StringBuilder results = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                results.append(line);
            }
            System.out.println(results.toString());
            return results.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: Something went wrong";
        }
	}
	
	public String getJsonString(Object object) {
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

	public String russasamfunnetRegister(String firstName, String lastName, String email, String password, String schoolName) {
		
		
		// opprette salt og kryptere passord
		String salt = BCrypt.gensalt();
		String encPassword = BCrypt.hashpw(password, salt);
		
		
		registerDAO.russasamfunnetRegister(firstName, lastName, email, encPassword, salt, schoolService.getSchool(schoolName));
		return loginService.loginAndGenerateAccessToken(email, password);
	}
	
	public String facebookRegisterNew(String accessToken, String email, String schoolName, int russYear, String birthdate) {
		Long schoolId = schoolService.getSchool(schoolName);
		System.out.println(schoolId);
		LoginStatus loginStatus = new LoginStatus();
		String userId;
		String appToken = "291199641408779|P9GEtCoB6TjzkZjbeAPTbcC2CV4";
		String appID = "291199641408779";
		String app_id = null;
		String firstName = null;
		String lastName = null;
		String birthday = "";
		JSONObject jsonObject4 = null;
		int ageRange;
		

		JSONObject jsonObject = new JSONObject();
		try {
			String url = "https://graph.facebook.com/debug_token?input_token=" + accessToken + "&access_token="
					+ appToken;
			String JSONString = this.uRLConnectionReader(url);

			JSONObject jsonObj = new JSONObject(JSONString);
			JSONObject jsonObj2 = jsonObj.getJSONObject("data");
			userId = jsonObj2.getString("user_id");
			app_id = jsonObj2.getString("app_id");
			String newUrl = "https://graph.facebook.com/me?fields=id,first_name,last_name,age_range&access_token=" + accessToken;
			JSONObject jsonObj3 = new JSONObject(this.uRLConnectionReader(newUrl));
			firstName = jsonObj3.getString("first_name");
			lastName = jsonObj3.getString("last_name");
			if(birthdate != null)
			{
			birthday = birthdate;
			}else {
				//get birthday from facebook.
			}
			jsonObject4 = jsonObj3.getJSONObject("age_range");
			ageRange = jsonObject4.getInt("min");
			
			
			if (app_id.equals(appID) && ageRange >= 17) {
				loginStatus.setLoginStatus(registerDAO.registerUserFBNew(userId, schoolId, firstName, lastName, email, russYear));

				loginStatus.setUserId(russDAO.getRussFromFacebookId(""+userId).getRussId());
				return getJsonString(loginStatus);
						
			} else {
				if(ageRange < 17)
				{
					loginStatus.setLoginStatus("User is younger than 17");
					return getJsonString(loginStatus);
				}else {
					
				System.out.println("Access token appId is not the same as application appId");
				System.out.println("Application appId");
				System.out.println(appID);
				System.out.println("Access token appId");
				System.out.println(app_id);
				loginStatus.setLoginStatus("Must log in through the facebook button in the app.");
				return getJsonString(loginStatus);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			loginStatus.setLoginStatus("Error: something went wrong.");
			return getJsonString(loginStatus);
	}
}
}
