package com.developer.lecai.entiey;

public class ZuiXinLotteryNotesEntity {

//	 "expect": "��803283��",
//     "result": "5+2+9=16(��,˫)"
	
	public String issuenum;
	private String result;
	
	
	public ZuiXinLotteryNotesEntity() {
		super();
	}


	public ZuiXinLotteryNotesEntity(String issuenum, String result) {
		super();
		this.issuenum = issuenum;
		this.result = result;
	}


	public String getExpect() {
		return issuenum;
	}


	public void setExpect(String issuenum) {
		this.issuenum = issuenum;
	}


	public String getResult() {
		return result;
	}


	public void setResult(String result) {
		this.result = result;
	}


	@Override
	public String toString() {
		return "ZuiXinLotteryNotesEntity [expect=" + issuenum + ", result=" + result + "]";
	}
	
	
}
