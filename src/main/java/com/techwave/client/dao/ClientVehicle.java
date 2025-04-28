package com.techwave.client.dao;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.regex.Matcher;

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
		ResponseEntity<String> response = restTemplate.exchange(
			url + "UpdateVehicle/" + v.getVehicleId(),
			HttpMethod.PUT,
			new HttpEntity<>(v),
			String.class
    	);
    	return response.getBody();
	}
	@Override
	public String DeleteVehicle(Vehicledb v) {
		// restTemplate.delete(url+"DeleteVehicle/"+v.getVehicleId(), String.class);
		// return "Vehicle deleted";
		ResponseEntity<String> response = restTemplate.exchange(
			url + "DeleteVehicle/" + v.getVehicleId(),
			HttpMethod.DELETE, 
			new HttpEntity<>(v),
			String.class
    	);
    	return response.getBody();
	}
	@Override
	public List<Vehicledb> searchVehicles(Vehicledb search, int price1, int price2) {
		try {
			
			// vlist = getAllVehicles().stream().filter(i->
				
			// 	i.getManufactureName().equalsIgnoreCase(search.getManufactureName()) &&
				
			// 	(i.getPrice()>price1 && i.getPrice()<price2) && 
				
			// 	i.getColor().equalsIgnoreCase(search.getColor()) &&
				
			// 	i.getbranchId().getbranchId().getUserId().equalsIgnoreCase(search.getbranchId().getbranchId().getUserId()) &&
				
			// 	i.getStock()>0 ).collect(Collectors.toList()); 

			List<Vehicledb> vlist = getAllVehicles();
			
			if (search.getManufactureName() != null && !search.getManufactureName().isBlank()) {
				vlist = vlist.stream().filter(i -> i.getManufactureName().equalsIgnoreCase(search.getManufactureName())).collect(Collectors.toList());
			}
			
			if (search.getColor() != null && !search.getColor().isBlank()) {
				vlist = vlist.stream().filter(i -> i.getColor().equalsIgnoreCase(search.getColor())).collect(Collectors.toList());
			}
			
			if (search.getbranchId().getbranchId().getUserId() != null 
			&& !search.getbranchId().getbranchId().getUserId().isBlank()) {
				vlist = vlist.stream().filter(i -> i.getbranchId().getbranchId().getUserId().equalsIgnoreCase(search.getbranchId().getbranchId().getUserId())).collect(Collectors.toList());
			}

			System.out.println("Default stock ");
			vlist = vlist.stream().filter(i -> i.getStock() > 0 
			&& i.getPrice() >= price1 
			&& i.getPrice() <= price2
			&& i.getSeatingCapacity()==search.getSeatingCapacity()).collect(Collectors.toList());
			
			if(vlist!=null) {
				return vlist;
			}
			else {
				throw new Exception();
			}
		}
		catch (Exception E) {
			return null;
		}
	}
	
	
	@Override
	public String requestVehicleStock(Vehicledb request) {
		return restTemplate.postForObject(url+"RequestVehicle/", request, String.class);
	}
	
	@Override
	public List<Vehicledb> displayRequests() {
		Vehicledb list[] = restTemplate.getForObject(url+"DisplayRequests", Vehicledb[].class);
		return Arrays.asList(list);
	}
	
	@Override
	public String approveVehicleRequest(Vehicledb request) {
		ResponseEntity<String> response = restTemplate.exchange(
			url + "ApproveVehicle/"+request.getVehicleId(),
			HttpMethod.PUT,
			new HttpEntity<>(null),
			String.class
    	);
    	return response.getBody();
	}
	
	@Override
	public String rejectVehicleRequest(Vehicledb request) {
		ResponseEntity<String> response = restTemplate.exchange(
			url + "RejectVehicle/"+request.getVehicleId(),
			HttpMethod.PUT,
			new HttpEntity<>(null),
			String.class
    	);
    	return response.getBody();
	}
}
