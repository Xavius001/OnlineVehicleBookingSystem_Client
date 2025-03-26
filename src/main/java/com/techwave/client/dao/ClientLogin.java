package com.techwave.client.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.techwave.client.model.Logindb;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ClientLogin extends ClientVehicle implements IClientLogin {
	
	@Autowired
	RestTemplate restTemplate;
	public String url="http://localhost:8081/"; //server

	@Override
	public List<Logindb> getAllLogins() {
		Logindb list[] = restTemplate.getForObject(url+"getAllLogins", Logindb[].class);
		return Arrays.asList(list);
	}
	
	@Override
	public Logindb getByLoginId(String loginId) {
		return restTemplate.getForObject(url+"getByLoginId/"+loginId, Logindb.class);
	}

	@Override
	public String AddLogin(Logindb L) {
		return restTemplate.postForObject(url+"AddLogin", L, String.class);
	}
	
	@Override
	public String UpdateLogin(Logindb L, String loginId) {
		// restTemplate.put(url+"UpdateLogin/"+loginId, L);
		// return "Login updated";
		ResponseEntity<String> response = restTemplate.exchange(
			url + "UpdateLogin/" + loginId,
			HttpMethod.PUT,
			new HttpEntity<>(L),
			String.class
    	);
    	return response.getBody();
	}
	
	@Override
	public String ApproveLogin(Logindb L, String loginId) {
		// restTemplate.put(url+"ApproveLogin/"+loginId, L);
		// return "Login approved.";
		ResponseEntity<String> response = restTemplate.exchange(
			url + "ApproveLogin/" + loginId, // URL with vehicle ID
			HttpMethod.PUT,                            // HTTP method
			new HttpEntity<>(L),                       // Request body wrapped in HttpEntity
			String.class                               // Expected response type
    	);
    	return response.getBody();
	}
	
	@Override
	public String RejectLogin(Logindb L, String loginId) {
		// restTemplate.put(url+"RejectLogin/"+loginId, L);
		// return "Login rejected.";
		ResponseEntity<String> response = restTemplate.exchange(
			url + "RejectLogin/" + loginId, // URL with vehicle ID
			HttpMethod.PUT,                            // HTTP method
			new HttpEntity<>(L),                       // Request body wrapped in HttpEntity
			String.class                               // Expected response type
    	);
    	return response.getBody();
	}

	@Override
	public String DeleteLogin(Logindb L) {
		// restTemplate.delete(url+"DeleteLogin/"+L.getUserId(), L.getUserId());
		// return "Login deleted";
		ResponseEntity<String> response = restTemplate.exchange(
			url + "DeleteLogin/" + L.getUserId(), // URL with vehicle ID
			HttpMethod.DELETE,                            // HTTP method
			new HttpEntity<>(L),                       // Request body wrapped in HttpEntity
			String.class                               // Expected response type
    	);
    	return response.getBody();
	}

	@Override
	public String verifyEmail(String loginId) {
		Logindb L = getByLoginId(loginId);
		// restTemplate.put(url+"verify/email="+loginId, L);
		// return "Login verified.";
		ResponseEntity<String> response = restTemplate.exchange(
			url + "verify/email=" + loginId, // URL with vehicle ID
			HttpMethod.PUT,                            // HTTP method
			new HttpEntity<>(L),                       // Request body wrapped in HttpEntity
			String.class                               // Expected response type
    	);
    	return response.getBody();
	}

	@Override
	public String validateLogin(Logindb L) {
		return restTemplate.getForObject(url+"ValidateLogin/"+L.getUserId(), String.class);	
	}
	
	public String getLoginStatus(Logindb L) {
		return restTemplate.getForObject(url+"GetLoginStatus/"+L.getUserId(), String.class);
	}
	
	@Override
	public String newUserRequest(Logindb newUser) {
		return restTemplate.postForObject(url+"NewUserRequest", newUser, String.class);	
	}

	// @Override
	// public String validateUserStatus(String loginId) {
	// 	return "restTemplate";
	// }
}
