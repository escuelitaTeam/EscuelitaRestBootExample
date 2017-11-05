package com.escuelita.rest.model;

public class Representative {
	private String account;
	private String name;
	private String address1;
	private String zone;
	
	
	
	
	public Representative(String account, String name, String address1, String zone) {
		super();
		this.account = account;
		this.name = name;
		this.address1 = address1;
		this.zone = zone;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	
	
	
}
