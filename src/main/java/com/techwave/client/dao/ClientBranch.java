package com.techwave.client.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.techwave.client.model.Branchdb;
import com.techwave.client.model.Logindb;

@Service
public class ClientBranch extends ClientCustomer implements IClientBranch {

	@Autowired
	RestTemplate restTemplate;
	public String url="http://localhost:8081/"; //server
	
	@Override
	public List<Branchdb> getAllBranches() {
		Branchdb list[] = restTemplate.getForObject(url+"getAllBranches", Branchdb[].class);
		return Arrays.asList(list);
	}

	@Override
	public Branchdb getByBranchId(String branchId) {
		return restTemplate.getForObject(url+"getByBranchId/"+branchId, Branchdb.class);
	}
	
	@Override
	public String validateBranchInfo(Branchdb info) {
		return restTemplate.postForObject(url+"validateBranchInfo", info, String.class);
	}

	@Override
	public String AddBranch(Branchdb B) {
		return restTemplate.postForObject(url+"AddBranch", B, String.class);
	}

	@Override
	public String DeleteBranch(Branchdb B) {
		// restTemplate.delete(url+"DeleteBranch", B);
		// return "Branch deleted.";
		ResponseEntity<String> response = restTemplate.exchange(
			url + "DeleteBranch/" + B.getbranchId().getUserId(), // URL with vehicle ID
			HttpMethod.DELETE,                            // HTTP method
			new HttpEntity<>(B),                       // Request body wrapped in HttpEntity
			String.class                               // Expected response type
    	);
    	return response.getBody();
	}

	@Override
	public List<String> getAllBranchIds() {
		String branchIds[] = restTemplate.getForObject(url+"GetAllBranchIds", String[].class);
		return Arrays.asList(branchIds);
	}
	
}
