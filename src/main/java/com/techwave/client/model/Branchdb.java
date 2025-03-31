package com.techwave.client.model;

public class Branchdb {
	
	private Logindb branchId;
	private String bLoc;
	private String address;
	private String pno;
	
	public Logindb getbranchId() {
		return branchId;
	}

	public void setbranchId(Logindb branchId) {
		this.branchId = branchId;
	}

	public String getbLoc() {
		return bLoc;
	}

	public void setbLoc(String bLoc) {
		this.bLoc = bLoc;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}
	
	public Branchdb(Logindb branchId, String bLoc, String address, String pno) {
		super();
		this.branchId = branchId;
		this.bLoc = bLoc;
		this.address = address;
		this.pno = pno;
	}

	public Branchdb() {
		super();
		this.branchId = new Logindb();
		this.bLoc = "";
		this.address = "";
		this.pno = "";
	}
	
}
