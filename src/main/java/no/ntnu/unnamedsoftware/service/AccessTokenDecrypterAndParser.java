package no.ntnu.unnamedsoftware.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenDecrypterAndParser {
	
	@Autowired
	EncryptorAndDecryptor encAndDec;
	
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
	
	private JSONObject getJSONObjectFromString(String jsonString)
	{
		JSONObject json = new JSONObject(jsonString);
		return json;
	}
	
	public Long getRussId(String accessToken)
	{
		JSONObject token = getJSONObjectFromString(decryptedAccessToken(accessToken));
		return token.getLong("russId");
	}

}
