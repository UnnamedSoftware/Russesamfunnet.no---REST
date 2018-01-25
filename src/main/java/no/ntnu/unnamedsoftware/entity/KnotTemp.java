package no.ntnu.unnamedsoftware.entity;

public class KnotTemp {
	
	private int knotId;
	private String knotDetails;
	private int schoolId;
	private boolean completed;
	private int russId;
	
	public KnotTemp(int knotId, String knotDetails, int schoolId, boolean completed, int russId) {
		this.knotId = knotId;
		this.knotDetails = knotDetails;
		this.schoolId = schoolId;
		this.completed = completed;
		this.russId = russId;
	}

	public int getKnotId() {
		return knotId;
	}

	public void setKnotId(int knotId) {
		this.knotId = knotId;
	}

	public String getKnotDetails() {
		return knotDetails;
	}

	public void setKnotDetails(String knotDetails) {
		this.knotDetails = knotDetails;
	}

	public int getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public int getRussId() {
		return russId;
	}

	public void setRussId(int russId) {
		this.russId = russId;
	}

	@Override
	public String toString() {
		return "KnotTemp [knotId=" + knotId + ", knotDetails=" + knotDetails + ", schoolId=" + schoolId + ", completed="
				+ completed + ", russId=" + russId + "]";
	}

}
