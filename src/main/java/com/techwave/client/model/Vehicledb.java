package com.techwave.client.model;

// check after updating server
public class Vehicledb {
	
	private String vehicleId;
	
	private String manufactureName;
	
	private String color;
	
	private Integer seatingCapacity;
	private Integer price;
	
	private Branchdb branchId;
	private Integer stock;

	private String status;
	
	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getManufactureName() {
		return manufactureName;
	}

	public void setManufactureName(String manufactureName) {
		this.manufactureName = manufactureName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getSeatingCapacity() {
		return seatingCapacity;
	}

	public void setSeatingCapacity(Integer seatingCapacity) {
		this.seatingCapacity = seatingCapacity;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Branchdb getbranchId() {
		return branchId;
	}

	public void setbranchId(Branchdb branchId) {
		this.branchId = branchId;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Vehicledb(
	String vehicleId, 
	String manufactureName, 
	String color, 
	Integer seatingCapacity, 
	Integer price, 
	Branchdb branchId,
	Integer stock, 
	String status) {
		super();
		this.vehicleId = vehicleId;
		this.manufactureName = manufactureName;
		this.color = color;
		this.seatingCapacity = seatingCapacity;
		this.price = price;
		this.branchId = branchId;
		this.stock = stock;
		this.status = status;
	}

	public Vehicledb() {
		super();
		this.vehicleId = "";
		this.manufactureName = "";
		this.color = "";
		this.seatingCapacity = -1;
		this.price = -1;
		this.branchId = new Branchdb();
		this.stock = -1;
		this.status = "";
	}

	@Override
	public String toString() {
		String price1 = this.price.toString();
		String stock1 = this.stock.toString();
		return "Vid-"+this.vehicleId+" Manu:"+this.manufactureName+" price:"+price1+" color:"+this.color+" stock:"+stock1+" branchid:"+this.getbranchId().getbranchId().getUserId();
	}
	
	
	
}
