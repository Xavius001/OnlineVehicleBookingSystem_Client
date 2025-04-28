package com.techwave.client.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.techwave.client.model.Logindb;

import com.techwave.client.dao.Client;

@Controller
@SessionAttributes("logindb")
public class LoginController {
	@Autowired
	Client cLogin;
	
	@RequestMapping("OnlineVehicleBookingSystem")
	public String homePage(Model M) {
		return "home";
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/register")
	public String registerPage(Model M) {
		M.addAttribute("logindb", new Logindb());
		return "register";
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/registered")
	public String registeredPage(@Valid @ModelAttribute("logindb") Logindb creationRequest, BindingResult bs, Model M) {
		try {
			if (bs.hasErrors()) {
				M.addAttribute("logindb", new Logindb());
				M.addAttribute("msg", 
						"Error in creating your account. Contact System Administrator");
				return "register";
			}
			else {
				M.addAttribute("logindb",creationRequest);
				M.addAttribute("msg", cLogin.newUserRequest(creationRequest));
				return "register";
			}
		}
		catch (Exception E) {
			M.addAttribute("logindb", new Logindb());
			M.addAttribute("msg", cLogin.newUserRequest(creationRequest));
			return "register";
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/login")
	public String loginPage(Model M) {
		M.addAttribute("logindb", new Logindb());
		return "login";
	}

	@RequestMapping("OnlineVehicleBookingSystem/verify")
	public String verifyPage(@RequestParam("email") String email, Model M) {
		M.addAttribute("email", email);
		return "verify";
	}

	@RequestMapping("OnlineVehicleBookingSystem/verified")
	public String verifiedPage(@RequestParam("email") String email, Model M) {
		cLogin.verifyEmail(email);
		M.addAttribute("email", email);
		M.addAttribute("msg", "Your email has been verified. Please login to continue.");
		return "verify";
	}
	
	// check after updating server
	@RequestMapping("OnlineVehicleBookingSystem/Validatelogin") // check after updating server
	public String ValidateLogin(@Valid @ModelAttribute("logindb") Logindb login, BindingResult bs, Model M) {
		try {
			if (bs.hasErrors()) {
				M.addAttribute("msg","Error. Contact System Admin for further details.");
				return "login";
			} 
			else {
				String role = cLogin.validateLogin(login);

				String status = cLogin.getLoginStatus(login);

				role = role.trim().toLowerCase();
            	status = status.trim().toLowerCase();
				
				if(!role.equals("Invalid")) {
					login.setRole(role);
				}
				if(!status.equals("Invalid")) {
					login.setStatus(status);
				}
				
				M.addAttribute("logindb",login);
				if (role.equals("admin") && status.equals("approved")) {
					return "redirect:/OnlineVehicleBookingSystem/AdminHomepage";
				} 
				
				else if (role.equals("branch") && status.equals("approved")) {
					return "redirect:/OnlineVehicleBookingSystem/BranchHomepage";
				}
				
				else if (role.equals("customer") && status.equals("approved")) {
					return "redirect:/OnlineVehicleBookingSystem/CustomerHomepage";
				} 
				
				else {
					M.addAttribute("logindb", new Logindb());
					M.addAttribute("msg", "Invalid Credentials");
					return "login";
				}
			}
		}
		catch (Exception E) {
			M.addAttribute("msg","Error. Contact System Admin for further details.");
			M.addAttribute("logindb", new Logindb());
			return "login";
		}
	}
		
	@RequestMapping("OnlineVehicleBookingSystem/logout")
	public String Logout(SessionStatus status, Model M) {
		status.setComplete();
		M.addAttribute("logindb", new Logindb());
		return "redirect:/OnlineVehicleBookingSystem/login";
	}

}
