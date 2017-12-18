package com.developer.lecai.entiey;

/**
 * �û�������Ϣʵ����
 * 
 * @author Administrator
 *
 */
public class UserInfoEntity {
	/** �û��˺� */
	private String account;
	/** �û��ǳ� */
	private String nickname;
	/** �û�����ǩ�� */
	private String signname;
	/** �û�ͷ��ͼƬ��ַ */
	private String avatar;
	/**�û����*/
	private String money;
	/**����ID*/
	private String userid;
	/**�ȼ�ͼƬ*/
	private String dengji;
	public UserInfoEntity() {
		super();
	}
	public UserInfoEntity(String account, String nickname, String signname, String avatar, String money, String userid,
			String dengji) {
		super();
		this.account = account;
		this.nickname = nickname;
		this.signname = signname;
		this.avatar = avatar;
		this.money = money;
		this.userid = userid;
		this.dengji = dengji;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSignname() {
		return signname;
	}
	public void setSignname(String signname) {
		this.signname = signname;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getDengji() {
		return dengji;
	}
	public void setDengji(String dengji) {
		this.dengji = dengji;
	}
	@Override
	public String toString() {
		return "UserInfoEntity [account=" + account + ", nickname=" + nickname + ", signname=" + signname + ", avatar="
				+ avatar + ", money=" + money + ", userid=" + userid + ", dengji=" + dengji + "]";
	}
	
	
	

}
