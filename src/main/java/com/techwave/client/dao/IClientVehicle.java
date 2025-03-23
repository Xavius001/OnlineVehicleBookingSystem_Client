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
	
	List<Vehicledb> searchVehicles(Vehicledb search, Integer price1, Integer price2);
	
	String requestVehicle(Vehicledb request);
	
	List<Vehicledb> displayRequests();
	
	String approveVehicle(Vehicledb request);

	String rejectVehicle(Vehicledb request);
}
