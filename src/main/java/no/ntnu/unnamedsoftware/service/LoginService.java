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

@Service
public class LoginService {

	@Autowired
	LoginDAO loginDAO;

	@Autowired
	ObjectMapper mapper;

	public String Login(String email, String password) {

		String loginSuccess = "false";

		String passwordInDatabase = null;

		try {
			passwordInDatabase = loginDAO.getPassword(email);
			if (passwordInDatabase == null) {
				loginSuccess = "false";
			} else {
				if (password.equals(loginDAO.getPassword(email))) {
					loginSuccess = "true";
				} else {
					loginSuccess = "false";
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return getJsonString(loginSuccess);

	}

	public String getJsonString(String loginSuccess) {
		String loginInJsonString = null;
		try {
			loginInJsonString = mapper.writeValueAsString(loginSuccess);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loginInJsonString;
	}

	public String facebookLogin(String accessToken) {
		String loginSuccess = "false";

		Boolean userIdInDatabase = null;
		String appToken = "291199641408779|P9GEtCoB6TjzkZjbeAPTbcC2CV4";
		String appID = "291199641408779";
		String app_id = null;
		try {
			String url ="https://graph.facebook.com/debug_token?input_token="+accessToken+"&access_token="+appToken;
			System.out.println(accessToken);
			String JSONString = this.uRLConnectionReader(url);
			
			JSONObject jsonObj = new JSONObject(JSONString);
			System.out.println("WTF?");
			JSONObject jsonObj2 = jsonObj.getJSONObject("data");
			System.out.println("WTF?");
			String userId = jsonObj2.getString("user_id");
			app_id = jsonObj2.getString("app_id");
			System.out.println(userId);
			userIdInDatabase = loginDAO.checkUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (app_id.equals(appID) && userIdInDatabase == false) {
			//createUser();
			return getJsonString("true");
		} else if(app_id.equals(appID)){
			
			return getJsonString("true");
			
		}else {
			return getJsonString("false");
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
