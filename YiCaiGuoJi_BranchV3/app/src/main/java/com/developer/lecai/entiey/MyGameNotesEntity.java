package com.developer.lecai.entiey;

import java.io.Serializable;

public class MyGameNotesEntity implements Serializable {
    /**
     * lcode : b201707220507077790
     * cpname : 三分彩
     * modelname : 96600.00/0.00%
     * issuenum : 20170722179
     * content : [7,3,2,3,2]
     * status : 1
     * typename : 五星直选_直选复式
     * num : 1
     * pmuch : 1
     * winfee : 0.0
     * resultnum : 2 1 4 8 2
     * mtype : 0
     * betttype : 1
     */

    private String lcode;
    private String cpname;
    private String modelname;
    private String issuenum;
    private String content;
    private int status;
    private String typename;
    private int num;
    private int pmuch;
    private double winfee;
    private String resultnum;
    private int mtype;
    private int betttype;
    private String time;
    private String payfee;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPayfee() {
        return payfee;
    }

    public void setPayfee(String payfee) {
        this.payfee = payfee;
    }

    public String getLcode() {
        return lcode;
    }

    public void setLcode(String lcode) {
        this.lcode = lcode;
    }

    public String getCpname() {
        return cpname;
    }

    public void setCpname(String cpname) {
        this.cpname = cpname;
    }

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public String getIssuenum() {
        return issuenum;
    }

    public void setIssuenum(String issuenum) {
        this.issuenum = issuenum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getPmuch() {
        return pmuch;
    }

    public void setPmuch(int pmuch) {
        this.pmuch = pmuch;
    }

    public double getWinfee() {
        return winfee;
    }

    public void setWinfee(double winfee) {
        this.winfee = winfee;
    }

    public String getResultnum() {
        return resultnum;
    }

    public void setResultnum(String resultnum) {
        this.resultnum = resultnum;
    }

    public int getMtype() {
        return mtype;
    }

    public void setMtype(int mtype) {
        this.mtype = mtype;
    }

    public int getBetttype() {
        return betttype;
    }

    public void setBetttype(int betttype) {
        this.betttype = betttype;
    }

    // "id": 2,
    // "type": "����28 -- 798824��",
    // "winmoney": 100,
    // "result": "9+4+2=15",
    // "items": "��,��,��,15,��",
    // "bttype": "��С��˫",
    // "userbttype": "��",
    // "money": 100,
    // "lkmoney": 200,
    // "addtime": "2016-12-17t09:11:38.463",
    // "totalcount": 2

	/*private String id;
    private String type;
	private String winmoney;
	private String result;
	private String items;
	private String bttype;
	private String userbttype;
	private String money;
	private String lkmoney;
	private String addtime;
	private String totalcount;

	public MyGameNotesEntity() {
		super();
	}

	public MyGameNotesEntity(String id, String type, String winmoney, String result, String items, String bttype,
			String userbttype, String money, String lkmoney, String addtime, String totalcount) {
		super();
		this.id = id;
		this.type = type;
		this.winmoney = winmoney;
		this.result = result;
		this.items = items;
		this.bttype = bttype;
		this.userbttype = userbttype;
		this.money = money;
		this.lkmoney = lkmoney;
		this.addtime = addtime;
		this.totalcount = totalcount;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWinmoney() {
		return winmoney;
	}

	public void setWinmoney(String winmoney) {
		this.winmoney = winmoney;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getItems() {
		return items;
	}

	public void setItems(String items) {
		this.items = items;
	}

	public String getBttype() {
		return bttype;
	}

	public void setBttype(String bttype) {
		this.bttype = bttype;
	}

	public String getUserbttype() {
		return userbttype;
	}

	public void setUserbttype(String userbttype) {
		this.userbttype = userbttype;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getLkmoney() {
		return lkmoney;
	}

	public void setLkmoney(String lkmoney) {
		this.lkmoney = lkmoney;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(String totalcount) {
		this.totalcount = totalcount;
	}

	@Override
	public String toString() {
		return "MyGameNotesEntity [id=" + id + ", type=" + type + ", winmoney=" + winmoney + ", result=" + result
				+ ", items=" + items + ", bttype=" + bttype + ", userbttype=" + userbttype + ", money=" + money
				+ ", lkmoney=" + lkmoney + ", addtime=" + addtime + ", totalcount=" + totalcount + "]";
	}*/


}
