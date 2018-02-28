package no.ntnu.unnamedsoftware.entity;

public class LoginObjectToReturn {
	
	private String loginStatus;
	private String accessToken;
	private int expiresInDays;
	
	public LoginObjectToReturn() {
		
	}
	
	public LoginObjectToReturn(String loginStatus, String accessToken, int expiresInDays) {
		this.loginStatus = loginStatus;
		this.accessToken = accessToken;
		this.expiresInDays = expiresInDays;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public int getExpiresInDays() {
		return expiresInDays;
	}

	public void setExpiresInDays(int expiresInDays) {
		this.expiresInDays = expiresInDays;
	}
	
	
	
}
