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

import org.mindrot.jbcrypt.BCrypt;

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
			Russ russInDB = russDAO.getUserRussFromEmail(email);
			if (russInDB == null) {
				return "User not in db";
			} else if (russInDB != null) { // else if (password.equals(russInDB.getRussPassword())) {

				// get password from db
				// get salt from db
				// salt password from client with salt from db
				// compare password from db and password from client

				String passwordInDB = russInDB.getRussPassword();
				String saltInDB = russInDB.getRussPasswordSalt();
				if (saltInDB != null) {
					String saltedPasswordFromClient = BCrypt.hashpw(password, saltInDB);
					if (passwordInDB.equals(saltedPasswordFromClient)) {
						// Login success
						String loginStatus = "Login success";

						AccessTokenCreation atc = new AccessTokenCreation(russInDB.getRussId(), russInDB.getEmail(),
								russInDB.getFirstName(), russInDB.getLastName(), russInDB.getRussYear());

						String JSONatc = getJsonString(atc);
						String accessToken = encAndDec.encrypt(JSONatc);
						int expiresInNoOfDays = 7;
						LoginObjectToReturn loginObjToReturn = new LoginObjectToReturn(loginStatus, accessToken,
								expiresInNoOfDays);
						loginObjToReturn.setRussId(russInDB.getRussId());
						return getJsonString(loginObjToReturn);
					} else {
						return "Incorrect password";
					}
				} else {
					return "An error occured";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Error";
	}

	public String Login(String email, String password) {

		LoginStatus loginStatus = new LoginStatus("false");


		try {
			if (russDAO.getUserRussFromEmail(email) == null) {
				loginStatus.setLoginStatus("User not in db");
			} 
			else if (password.equals(russDAO.getUserRussFromEmail(email).getRussPassword())) {
					loginStatus.setLoginStatus("Login success");
					
				} else {
					System.out.println(russDAO.getUserRussFromEmail(email).getRussPassword());
					loginStatus.setLoginStatus("Incorrect password");
				}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(loginStatus.getLoginStatus().toString());
		loginStatus.setUserId(russDAO.getUserRussFromEmail(email).getRussId());
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
		LoginStatus loginStatus;
		String userId = null;;
		String appToken = "291199641408779|P9GEtCoB6TjzkZjbeAPTbcC2CV4";
		String appID = "291199641408779";
		String app_id = null;
		Boolean userIsInDb = false;
		JSONObject jsonObject = new JSONObject();
		try {
			String url = "https://graph.facebook.com/debug_token?input_token=" + accessToken 
					+ "&access_token=" + appToken;
			String JSONString = this.uRLConnectionReader(url);

			JSONObject jsonObj = new JSONObject(JSONString);
			JSONObject jsonObj2 = jsonObj.getJSONObject("data");
			userId = jsonObj2.getString("user_id");
			app_id = jsonObj2.getString("app_id");
			userIsInDb = loginDAO.checkUserFB(userId);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (app_id.equals(appID) && userIsInDb == false) {
			System.out.println(app_id + "    " + appID);
			System.out.println(userIsInDb);
			return getJsonString(new LoginStatus("User not in db"));
		} else if (app_id.equals(appID)) {
			loginStatus = new LoginStatus("Login success");
			loginStatus.setUserId(russDAO.getRussFromFacebookId(userId).getRussId());
			return getJsonString(loginStatus);
		} else {
			return getJsonString(new LoginStatus("Wrong appToken"));
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
}
