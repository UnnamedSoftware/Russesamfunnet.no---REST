package no.ntnu.unnamedsoftware.entity;

public class LoginStatus {
	private String loginStatus;
	Long userId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public LoginStatus(String loginStatus) {
		super();
		this.loginStatus = loginStatus;
	}

	public String getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}

}
