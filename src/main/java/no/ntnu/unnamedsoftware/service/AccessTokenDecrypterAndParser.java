package no.ntnu.unnamedsoftware.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import no.ntnu.unnamedsoftware.DAO.RussDAO;

@Service
public class AccessTokenDecrypterAndParser {

	@Autowired
	EncryptorAndDecryptor encAndDec;
	
	@Autowired
	RussDAO russDAO;

	public String decryptedAccessToken(String accessToken) {
		try {
			String[] parts = accessToken.split(" ");
			StringBuilder sb = new StringBuilder();
			int i = 0;
			for (String p : parts) {
				if (i < 1) {
					sb.append(p);
				} else {
					sb.append("+" + p);
				}
				i++;
			}
			String tokenFixed = sb.toString();
			String decrypted = encAndDec.decrypt(tokenFixed);
			System.out.println(decrypted);
			return decrypted;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Error";
	}

	private JSONObject getJSONObjectFromString(String jsonString) {
		JSONObject json = new JSONObject(jsonString);
		return json;
	}

	public Long getRussId(String accessToken) {
		JSONObject token = getJSONObjectFromString(decryptedAccessToken(accessToken));
		return token.getLong("russId");
	}

	/**
	 * 
	 * @param accessToken
	 * @return russId
	 */
	public Long decryptFacebookToken(String accessToken) {
		String appToken = "291199641408779|P9GEtCoB6TjzkZjbeAPTbcC2CV4";
		String appID = "291199641408779";
		String userId;
		String app_id;
		JSONObject jsonObject = new JSONObject();
		try {
			String url = "https://graph.facebook.com/debug_token?input_token=" + accessToken + "&access_token="
					+ appToken;
			String JSONString = this.uRLConnectionReader(url);

			JSONObject jsonObj = new JSONObject(JSONString);
			JSONObject jsonObj2 = jsonObj.getJSONObject("data");
			userId = jsonObj2.getString("user_id");
			app_id = jsonObj2.getString("app_id");
			Long russId = russDAO.getRussFromFacebookId(userId).getRussId();
			System.out.println(Long.valueOf(userId));
			return russId;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

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
