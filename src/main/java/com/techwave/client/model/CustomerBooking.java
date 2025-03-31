package com.techwave.client.model;

public class CustomerBooking {
	
	private String bookingId;
	private Customerdb custId;
	private Vehicledb vehicleId;
	private Branchdb branchId;
	private String status;
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public Customerdb getCustId() {
		return custId;
	}

	public void setCustId(Customerdb custId) {
		this.custId = custId;
	}

	public Vehicledb getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(Vehicledb vehicleId) {
		this.vehicleId = vehicleId;
	}

	public Branchdb getBranchId() {
		return branchId;
	}

	public void setBranchId(Branchdb branchId) {
		this.branchId = branchId;
	}

	public CustomerBooking(
		String bookingId, 
		Customerdb custId, 
		Vehicledb vehicleId, 
		Branchdb branchId, 
		String status) {
		super();
		this.bookingId = bookingId;
		this.custId = custId;
		this.vehicleId = vehicleId;
		this.branchId = branchId;
		this.status = status;
	}

	public CustomerBooking() {}
	
}
