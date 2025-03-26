package com.techwave.client.dao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techwave.client.model.Branchdb;
import com.techwave.client.model.Vehicledb;

public class ClientVehicle implements IClientVehicle {
	
	@Autowired
	RestTemplate restTemplate;
	public String url="http://localhost:8081/"; //server
	
	@Override
	public List<Vehicledb> getAllVehicles() {
		Vehicledb list[] = restTemplate.getForObject(url+"getAllVehicles", Vehicledb[].class);
		return Arrays.asList(list);
	}
	@Override
	public Vehicledb getByVehicleId(String vehicleId) {
		return restTemplate.getForObject(url+"getByVehicleId/"+vehicleId, Vehicledb.class);
	}
	@Override
	public String AddVehicle(Vehicledb v) {
		return restTemplate.postForObject(url+"AddVehicle", v, String.class);
	}
	@Override
	public String UpdateVehicle(Vehicledb v) {
		// restTemplate.put(url+"UpdateVehicle/"+v.getVehicleId(), v);
		// return "vehicle updated";
		ResponseEntity<String> response = restTemplate.exchange(
			url + "UpdateVehicle/" + v.getVehicleId(), // URL with vehicle ID
			HttpMethod.PUT,                            // HTTP method
			new HttpEntity<>(v),                       // Request body wrapped in HttpEntity
			String.class                               // Expected response type
    	);
    	return response.getBody();
	}
	@Override
	public String DeleteVehicle(Vehicledb v) {
		// restTemplate.delete(url+"DeleteVehicle/"+v.getVehicleId(), String.class);
		// return "Vehicle deleted";
		ResponseEntity<String> response = restTemplate.exchange(
			url + "DeleteVehicle/" + v.getVehicleId(), // URL with vehicle ID
			HttpMethod.DELETE,                            // HTTP method
			new HttpEntity<>(v),                       // Request body wrapped in HttpEntity
			String.class                               // Expected response type
    	);
    	return response.getBody();
	}
	@Override
	public List<Vehicledb> searchVehicles(Vehicledb search, Integer price1, Integer price2) {
		Vehicledb list[] = restTemplate.getForObject(url+"SearchVehicles/"+price1+"/"+price2, Vehicledb[].class);
		return Arrays.asList(list);
	}
	
	@Override
	public String requestVehicle(Vehicledb request) {
		return restTemplate.postForObject(url+"RequestVehicle", request, String.class);
	}
	
	@Override
	public List<Vehicledb> displayRequests() {
		Vehicledb list[] = restTemplate.getForObject(url+"DisplayRequests", Vehicledb[].class);
		return Arrays.asList(list);
	}
	
	@Override
	public String approveVehicle(Vehicledb request) {
		// restTemplate.put(url+"ApproveVehicle", request, String.class);
		// return "Vehicle approved";
		ResponseEntity<String> response = restTemplate.exchange(
			url + "ApproveVehicle/", // URL with vehicle ID
			HttpMethod.PUT,                            // HTTP method
			new HttpEntity<>(request),                       // Request body wrapped in HttpEntity
			String.class                               // Expected response type
    	);
    	return response.getBody();
	}
	
	@Override
	public String rejectVehicle(Vehicledb request) {
		// restTemplate.put(url+"RejectVehicle", request, String.class);
		// return "Vehicle rejected";
		ResponseEntity<String> response = restTemplate.exchange(
			url + "RejectVehicle/", // URL with vehicle ID
			HttpMethod.PUT,                            // HTTP method
			new HttpEntity<>(request),                       // Request body wrapped in HttpEntity
			String.class                               // Expected response type
    	);
    	return response.getBody();
	}
}
