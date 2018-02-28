package no.ntnu.unnamedsoftware.entity;

public class AccessTokenCreation {
	
	private Long russId;
	private String email;
	private String firstName;
	private String lastName;
	private int russYear;
	
	public AccessTokenCreation() {
		
	}
	
	public AccessTokenCreation(Long russId, String email, String firstName, String lastName, int russYear) {
		super();
		this.russId = russId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.russYear = russYear;
	}

	public Long getRussId() {
		return russId;
	}

	public void setRussId(Long russId) {
		this.russId = russId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getRussYear() {
		return russYear;
	}

	public void setRussYear(int russYear) {
		this.russYear = russYear;
	}
	
	
	

}
