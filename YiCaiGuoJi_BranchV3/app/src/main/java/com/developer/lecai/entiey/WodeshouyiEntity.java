package com.developer.lecai.entiey;

public class WodeshouyiEntity {
	
//	"nickname":�ǳ�
//	"money":����
//	"status":�Ƿ���������
//	"backrate":Ͷע����


	private String nickname;
	private String money;
	private String status;
	private String backrate;
	public WodeshouyiEntity() {
		super();
	}
	public WodeshouyiEntity(String nickname, String money, String status, String backrate) {
		super();
		this.nickname = nickname;
		this.money = money;
		this.status = status;
		this.backrate = backrate;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getBackrate() {
		return backrate;
	}
	public void setBackrate(String backrate) {
		this.backrate = backrate;
	}
	@Override
	public String toString() {
		return "WodeshouyiEntity [nickname=" + nickname + ", money=" + money + ", status=" + status + ", backrate="
				+ backrate + "]";
	}
	
	
	
}
