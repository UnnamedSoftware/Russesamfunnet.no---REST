package no.ntnu.unnamedsoftware.service;

import org.jasypt.*;
import org.jasypt.util.text.BasicTextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class EncryptorAndDecryptor {

	private String myPassword = "russesamfunnet";
	private BasicTextEncryptor textEncryptor;
	
	public EncryptorAndDecryptor(){
		textEncryptor = new BasicTextEncryptor();
		textEncryptor.setPassword(myPassword);
	}
	
	public String encrypt(String enc) {
		return textEncryptor.encrypt(enc);
		
	}
	
	public String decrypt(String dec) {
		return textEncryptor.decrypt(dec);
	}
	
	//String accessToken = textEncryptor.encrypt(JSONruss);
	
}
