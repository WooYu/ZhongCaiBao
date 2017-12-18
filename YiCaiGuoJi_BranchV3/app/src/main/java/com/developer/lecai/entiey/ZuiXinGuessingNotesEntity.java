package com.developer.lecai.entiey;

public class ZuiXinGuessingNotesEntity {


	public String issuenum;
	private String status;
	private String time;
	public int sealtimes;
	public ZuiXinGuessingNotesEntity() {
		super();
	}


	public ZuiXinGuessingNotesEntity(String issuenum, String status) {
		super();
		this.issuenum = issuenum;
		this.status = status;
	}


	public String getExpect() {
		return issuenum;
	}


	public void setExpect(String issuenum) {
		this.issuenum = issuenum;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "ZuiXinGuessingNotesEntity [expect=" + issuenum + ", time=" + issuenum + "]";
	}
	
	

}
