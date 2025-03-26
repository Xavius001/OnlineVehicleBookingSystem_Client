package com.techwave.client.dao;

import java.util.List;

import com.techwave.client.model.Logindb;

public interface IClientLogin {
	
	List<Logindb> getAllLogins(); //get
	
	Logindb getByLoginId(String loginId); //get
	
	String AddLogin(Logindb L); //post
	
	String UpdateLogin(Logindb L, String loginId); //put
	
	String ApproveLogin(Logindb L, String loginId);
	
	String RejectLogin(Logindb L, String loginId);
	
	String DeleteLogin(Logindb L); //delete

	String verifyEmail(String loginId);
	
	String validateLogin(Logindb L); //
	
	String newUserRequest(Logindb newUser);
	
	// String validateUserStatus(String userId);
	
}
