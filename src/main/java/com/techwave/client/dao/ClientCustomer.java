package com.techwave.client.dao;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.techwave.client.model.Customerdb;

public class ClientCustomer extends ClientLogin implements IClientCustomer {
	
	@Autowired
	RestTemplate restTemplate;
	public String url="http://localhost:8081/"; //server
	@Override
	public List<Customerdb> getAllCust() {
		Customerdb list[] = restTemplate.getForObject(url+"getAllCust", Customerdb[].class);
		return Arrays.asList(list);
	}
	@Override
	public Customerdb getByCustId(String custId) {
		return restTemplate.getForObject(url+"getByCustId/"+custId, Customerdb.class);
	}
	@Override
	public String AddCust(Customerdb C) {
		return restTemplate.postForObject(url+"AddCust", C, String.class);
	}
	@Override
	public String DeleteCust(Customerdb C) {
		restTemplate.delete(url+"DeleteCust/"+C.getcustId().getUserId(), String.class);
		return "Customer deleted";
	}
	
	@Override
	public String validateCustInfo(Customerdb info) {
		return restTemplate.postForObject(url+"validateCustInfo", info, String.class);
	}
	
}
