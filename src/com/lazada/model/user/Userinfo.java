package com.lazada.model.user;

import java.util.ArrayList;

public class Userinfo {

	private String id;
	private String name;
	private String password;
	private String money;
	private String paytime;
	private String limittime;
	private String pcnum;
	private ArrayList<String> pclist;
	private String catchnum;
	private String areadynum;
	private String other;
	private String currentmac;
	
	public Userinfo() {
    }
	
	public Userinfo(String id, String name, String password, String money,
			String paytime, String limittime,String pcnum,String catchnum,String other) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.money = money;
		this.paytime = paytime;
		this.limittime = limittime;
		this.pcnum = pcnum;
		this.catchnum = catchnum;
		this.pclist=new ArrayList<>();
		this.other=other;
		this.areadynum="0";
	}
	
	public Userinfo(String id, String name, String password, String money, String paytime, String limittime,
			String pcnum, ArrayList<String> pclist, String catchnum,String other) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.money = money;
		this.paytime = paytime;
		this.limittime = limittime;
		this.pcnum = pcnum;
		this.pclist = pclist;
		this.catchnum = catchnum;
		this.other=other;
	}

	public Userinfo(String name, String password, String areadycatchnum,String currentmac) {
		this.name = name;
		this.password = password;
		this.areadynum=areadycatchnum;
		this.currentmac=currentmac;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getPaytime() {
		return paytime;
	}
	public void setPaytime(String paytime) {
		this.paytime = paytime;
	}
	public String getLimittime() {
		return limittime;
	}
	public void setLimittime(String limittime) {
		this.limittime = limittime;
	}
	public String getPcnum() {
		return pcnum;
	}
	public void setPcnum(String pcnum) {
		this.pcnum = pcnum;
	}
	public String getCatchnum() {
		return catchnum;
	}
	public void setCatchnum(String catchnum) {
		this.catchnum = catchnum;
	}

	public ArrayList<String> getPclist() {
		return pclist;
	}

	public void setPclist(ArrayList<String> pclist) {
		this.pclist = pclist;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public String getAreadynum() {
		return areadynum;
	}

	public void setAreadynum(String areadynum) {
		this.areadynum = areadynum;
	}

	public String getCurrentmac() {
		return currentmac;
	}

	public void setCurrentmac(String currentmac) {
		this.currentmac = currentmac;
	}
	
	
	
}
