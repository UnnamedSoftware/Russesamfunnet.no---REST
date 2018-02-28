package no.ntnu.unnamedsoftware.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.DAO.LoginDAO;
import no.ntnu.unnamedsoftware.DAO.RussDAO;
import no.ntnu.unnamedsoftware.entity.AccessTokenCreation;
import no.ntnu.unnamedsoftware.entity.LoginObjectToReturn;
import no.ntnu.unnamedsoftware.entity.LoginStatus;
import no.ntnu.unnamedsoftware.entity.Russ;

import org.jasypt.*;
import org.jasypt.util.text.BasicTextEncryptor;

@Service
public class LoginService {

	@Autowired
	LoginDAO loginDAO;

	@Autowired
	RussDAO russDAO;

	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	EncryptorAndDecryptor encAndDec;
	
	public String loginAndGenerateAccessToken(String email, String password) {
		
		// Check to see if user exists
		try {
			Russ russInDB = loginDAO.getRuss(email);
			if (russInDB == null) {
				return "User not in db";
			} 
			else if (password.equals(russInDB.getRussPassword())) {
				// Login success 
				String loginStatus = "Login success";
				
				// (Long russId, String email, String firstName, String lastName, int russYear) {
				AccessTokenCreation atc = new AccessTokenCreation(russInDB.getRussId(), russInDB.getEmail(), russInDB.getFirstName(), russInDB.getLastName(), russInDB.getRussYear());
				
				
				//String JSONrussTest = getJsonString(russInDB);
				//System.out.println("***********" +  JSONrussTest);
				String JSONatc = getJsonString(atc);
				//String myPassword = "russesamfunnet";
				//BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
				//textEncryptor.setPassword(myPassword);
				//String accessToken = textEncryptor.encrypt(JSONruss);
				String accessToken = encAndDec.encrypt(JSONatc);
				int expiresInNoOfDays = 7;
				LoginObjectToReturn loginObjToReturn = new LoginObjectToReturn(loginStatus, accessToken, expiresInNoOfDays);
				return getJsonString(loginObjToReturn);	
				} else {
					System.out.println(russInDB.getRussPassword());
					return "Incorrect password";
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Error";
	}
	
	public String decryptedAccessToken(String accessToken) {
		try {
			String[] parts = accessToken.split(" ");
			StringBuilder sb = new StringBuilder();
			int i = 0;
			for(String p: parts) {
				if(i < 1) {
					sb.append(p);
				}
				else{
					sb.append("+"+p);
				}
				i++;
			}
			String tokenFixed = sb.toString();
			String decrypted = encAndDec.decrypt(tokenFixed);
			System.out.println(decrypted);
			return decrypted;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "Error";		
	}
	

	public String Login(String email, String password) {

		LoginStatus loginStatus = new LoginStatus("false");


		try {
			if (loginDAO.getRuss(email) == null) {
				loginStatus.setLoginStatus("User not in db");
			} 
			else if (password.equals(loginDAO.getRuss(email).getRussPassword())) {
					loginStatus.setLoginStatus("Login success");
					
				} else {
					System.out.println(loginDAO.getRuss(email).getRussPassword());
					loginStatus.setLoginStatus("Incorrect password");
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(loginStatus.getLoginStatus().toString());
		loginStatus.setUserId(loginDAO.getRuss(email).getRussId());
		return getJsonString(loginStatus);

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

	public String facebookLogin(String accessToken) {

		String appToken = "291199641408779|P9GEtCoB6TjzkZjbeAPTbcC2CV4";
		String appID = "291199641408779";
		String app_id = null;
		Boolean userIsInDb = false;
		JSONObject jsonObject = new JSONObject();
		try {
			String url = "https://graph.facebook.com/debug_token?input_token=" + accessToken + "&access_token="
					+ appToken;
			System.out.println(accessToken);
			String JSONString = this.uRLConnectionReader(url);

			JSONObject jsonObj = new JSONObject(JSONString);
			System.out.println("WTF?");
			JSONObject jsonObj2 = jsonObj.getJSONObject("data");
			System.out.println("WTF?");
			String userId = jsonObj2.getString("user_id");
			app_id = jsonObj2.getString("app_id");
			System.out.println(userId);
			userIsInDb = loginDAO.checkUser(userId);
			//russIdAlt

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (app_id.equals(appID) && userIsInDb == false) {
			// createUser();
			return getJsonString(new LoginStatus("User not in db"));
		} else if (app_id.equals(appID)) {
			return getJsonString(new LoginStatus("Login success"));

		} else {
			return getJsonString(new LoginStatus("Wrong appToken"));
		}

	}

	public String uRLConnectionReader(String urlString) {
		try {
			System.out.println(urlString);
			HttpsURLConnection con = (HttpsURLConnection) new URL(urlString).openConnection();
			con.setRequestMethod("GET");
			con.setDoOutput(true);
			con.connect();
			System.out.println("connection");
			InputStreamReader inputStream = new InputStreamReader(con.getInputStream());
			System.out.println("Stream reader");
			BufferedReader reader = new BufferedReader(inputStream);
			System.out.println("Buffered reader");
			StringBuilder results = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				results.append(line);
			}
			System.out.println(results.toString());
			return results.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "ERROR: Something went wrong";
	}
}
