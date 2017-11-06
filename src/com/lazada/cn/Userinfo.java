package com.lazada.cn;


public class Userinfo {

	private String id;
	private String name;
	private String password;
	private String money;
	private String paytime;
	private String limittime;
	
	
	public Userinfo(String id, String name, String password, String money,
			String paytime, String limittime) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.money = money;
		this.paytime = paytime;
		this.limittime = limittime;
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
	
}
