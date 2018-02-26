package no.ntnu.unnamedsoftware.entity;

public class KnotTemp {
	
	private Long knotId;
	private String knotName;
	private String knotDetails;
	private Long schoolId;
	private boolean completed;
	private Long russId;
	
	public KnotTemp(Long long1, String knotName, String knotDetails, Long theSchoolId, boolean completed, Long russId) {
		this.knotId = long1;
		this.knotName = knotName;
		this.knotDetails = knotDetails;
		this.schoolId = theSchoolId;
		this.completed = completed;
		this.russId = russId;
	}

	public Long getKnotId() {
		return knotId;
	}

	public void setKnotId(Long knotId) {
		this.knotId = knotId;
	}
	
	public String getKnotName() {
		return knotName;
	}

	public void setKnotName(String knotName) {
		this.knotName = knotName;
	}

	public String getKnotDetails() {
		return knotDetails;
	}

	public void setKnotDetails(String knotDetails) {
		this.knotDetails = knotDetails;
	}

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public long getRussId() {
		return russId;
	}

	public void setRussId(Long russId) {
		this.russId = russId;
	}

	@Override
	public String toString() {
		return "KnotTemp [knotId=" + knotId + ", knotDetails=" + knotDetails + ", schoolId=" + schoolId + ", completed="
				+ completed + ", russId=" + russId + "]";
	}

}
