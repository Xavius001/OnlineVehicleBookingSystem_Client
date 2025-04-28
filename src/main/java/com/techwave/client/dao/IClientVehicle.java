package com.techwave.client.dao;

import java.util.List;

import com.techwave.client.model.Branchdb;
import com.techwave.client.model.Vehicledb;

public interface IClientVehicle {
	
	List<Vehicledb> getAllVehicles(); //get
	
	Vehicledb getByVehicleId(String vehicleId); //get
	
	String AddVehicle(Vehicledb v); //post
	
	String UpdateVehicle(Vehicledb v); //put
	
	String DeleteVehicle(Vehicledb v); //delete
	
	List<Vehicledb> searchVehicles(Vehicledb search, int price1, int price2);
	
	String requestVehicleStock(Vehicledb request);
	
	List<Vehicledb> displayRequests();
	
	String approveVehicleRequest(Vehicledb request);

	String rejectVehicleRequest(Vehicledb request);
}
