package no.ntnu.unnamedsoftware.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import no.ntnu.unnamedsoftware.DAO.RegisterDAO;

@Service
public class RegisterService {
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	RegisterDAO registerDAO;
	
	public String facebookRegister(String accessToken, String birthdate, String schoolId) {
		
		String userId;
		String appToken = "291199641408779|P9GEtCoB6TjzkZjbeAPTbcC2CV4";
		String appID = "291199641408779";
		String app_id = null;
		String firstName = null;
		String lastName = null;

		JSONObject jsonObject = new JSONObject();
		try {
			String url ="https://graph.facebook.com/debug_token?input_token="+accessToken+"&access_token="+appToken;
			System.out.println(accessToken);
			String JSONString = this.uRLConnectionReader(url);
			
			JSONObject jsonObj = new JSONObject(JSONString);
			JSONObject jsonObj2 = jsonObj.getJSONObject("data");
			userId = jsonObj2.getString("user_id");
			System.out.println(userId);
			app_id = jsonObj2.getString("app_id");
			System.out.println(app_id);
			String newUrl = "https://graph.facebook.com/me?fields=id,first_name,last_name";
			
			JSONObject jsonObj3 = new JSONObject(this.uRLConnectionReader(url));
			firstName = jsonObj3.getString("first_name");
			lastName = jsonObj3.getString("last_name");
			
			
			if(app_id.equals(appID))
			{
				return registerDAO.registerUser(userId, birthdate, schoolId, firstName, lastName);
			}else
			{
				System.out.println("Access token appId is not the same as application appId");
				System.out.println("Application appId");
				System.out.println(appID);
				System.out.println("Access token appId");
				System.out.println(app_id);
				return "Must log in through the facebook button in the app.";
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return e.getStackTrace().toString();
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
