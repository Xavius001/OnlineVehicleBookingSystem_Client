package com.techwave.client.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

public class Customerdb {
	
	private Logindb custId;
	private String name;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private LocalDate dob;
	private String address;
	private String pno;
	private String occupation;

	public Logindb getcustId() {
		return custId;
	}

	public void setcustId(Logindb custId) {
		this.custId = custId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
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

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Customerdb(Logindb custId, String name, LocalDate dob, String address,
			String pno, String occupation) {
		super();
		this.custId = custId;
		this.name = name;
		this.dob = dob;
		this.address = address;
		this.pno = pno;
		this.occupation = occupation;
	}

	public Customerdb() {}
	
}
