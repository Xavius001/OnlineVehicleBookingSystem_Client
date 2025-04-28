package com.techwave.client.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.techwave.client.dao.Client;
import com.techwave.client.model.Branchdb;
import com.techwave.client.model.CustomerBooking;
import com.techwave.client.model.Logindb;
import com.techwave.client.model.Vehicledb;

@Controller
@SessionAttributes("logindb")
public class BranchController {
	
	@Autowired
	Client cBranch;
	
	@RequestMapping("OnlineVehicleBookingSystem/BranchHomepage")
	public String branchPage(@SessionAttribute("logindb") Logindb L, Model M) {
		if(cBranch.getByBranchId(L.getUserId())!=null) {
			M.addAttribute("display", false);
			M.addAttribute("branchdb", cBranch.getByBranchId(L.getUserId()));
			return "bHome";
		}
		else {
			M.addAttribute("display", true);
			M.addAttribute("branchdb", new Branchdb());
			return "bHome";
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/BranchHomepage/SaveInfo")
	public String branchSave(@SessionAttribute("logindb") Logindb L, @Valid @ModelAttribute("branchdb") Branchdb B, BindingResult bs, Model M) {
		try {
			if(bs.hasErrors()) {
				M.addAttribute("logindb", L);
				M.addAttribute("branchdb", B);
				M.addAttribute("display", true);
				M.addAttribute("msg", "Error in saving your info. Contact System Administrator");
				return "register";
			}
			else {
				B.setbranchId(L);
				String msg = cBranch.validateBranchInfo(B);
				if(msg.equals("Valid Credentials")) {
					cBranch.AddBranch(B);
					M.addAttribute("msg", msg);
					M.addAttribute("display", false);
					M.addAttribute("branchdb", B);
					return "bHome";
				}
				else {
					M.addAttribute("display", true);
					M.addAttribute("msg", msg);
					M.addAttribute("branchdb", B);
					return "bHome";
				}
			}
		}
		catch(Exception E) {
			M.addAttribute("logindb", L);
			M.addAttribute("branchdb", new Branchdb());
			M.addAttribute("display", true);
			M.addAttribute("msg", "Error in saving your info. Contact System Administrator");
			return "bHome";
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/ApproveVehicleBooking")
	public String approvePage(@SessionAttribute("logindb") Logindb L, Model M) {
		try {
			List<CustomerBooking> cbList;
			cbList = cBranch.getAllBookings().stream()
    		.filter(i -> i.getBranchId() != null 
              && i.getBranchId().getbranchId() != null 
              && i.getBranchId().getbranchId().getUserId() != null 
              && i.getBranchId().getbranchId().getUserId().equals(L.getUserId()))
    		.collect(Collectors.toList());
			System.out.println("cbList: "+cbList.toString());
			if(cbList!=null) {
				M.addAttribute("cbList", cbList);
				return "bAprVehBook";
			} else {
				System.out.println("Exception thrown.");
				throw new Exception();
			}
		}
		catch(Exception E) {
			M.addAttribute("cbList", null);
			M.addAttribute("msg", "No bookings available.");
			return "bAprVehBook";
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/ApproveVehicleBooking/ApproveBooking")
	public String approveBookingPage(@SessionAttribute("logindb") Logindb L, @RequestParam(name="bookings[]",required=false) String[] bookings, Model M) {
		try {
			if(bookings!=null) {
				for(String s : bookings) {
					CustomerBooking cb = cBranch.getByBookingId(s);
					String msg = cBranch.ApproveBooking(cb, s);
					M.addAttribute("msg", msg);
				}
				List<CustomerBooking> cbList = cBranch.getAllBookings();
				M.addAttribute("cbList", cbList);
			}
			else {
				throw new Exception();
			}
		} 
		catch(Exception E) {
			M.addAttribute("msg", "Error contact system admin.");
		}
		return approvePage(L, M);
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/ApproveVehicleBooking/RejectBooking")
	public String rejectBookingPage(@SessionAttribute("logindb") Logindb L, @RequestParam(name="bookings[]",required=false) String[] bookings, Model M) {
		try {
			if(bookings!=null) {
				for(String s : bookings) {
					CustomerBooking cb = cBranch.getByBookingId(s);
					String msg = cBranch.RejectBooking(cb, s);
					M.addAttribute("msg", msg);
				}
				List<CustomerBooking> cbList = cBranch.getAllBookings();
				M.addAttribute("cbList", cbList);
			}
			else {
				throw new Exception();
			}
		} 
		catch(Exception E) {
			M.addAttribute("msg", "Error contact system admin.");
		}
		return approvePage(L, M);
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/RequestNewVehicles")
	public String requestPage(@ModelAttribute("vehicledb") Vehicledb request, Model M) {
		M.addAttribute("vehicledb", new Vehicledb());
		M.addAttribute("vlist", cBranch.getAllVehicles());
		return "bReqNewVeh";
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/RequestNewVehicles/Request")
	public String requestPage2(@Valid @ModelAttribute("vehicledb") Vehicledb request, BindingResult bs, Model M) {
		try {
			if(bs.hasErrors()) {
				M.addAttribute("msg", "Error. Contact system admin for further assistence.");
				return requestPage(request, M);
			}
			else {
				// System.out.println(request);
				// if( (request.getVehicleId().isBlank() || request.getVehicleId().isEmpty())
				// 		|| (request.getManufactureName().isBlank() || request.getManufactureName().isEmpty()) 
				// 		|| request.getPrice()==null || (request.getColor().isBlank() || request.getColor().isEmpty()) 
				// 		|| request.getStock()==null 
				// 		|| (request.getbranchId().getbranchId().getUserId().isBlank() || request.getbranchId().getbranchId().getUserId().isEmpty()) ) {
				// 	throw new Exception();
				// }
				System.out.println("Before vehicle request");
				M.addAttribute("msg", cBranch.requestVehicleStock(request));
				System.out.println("After vehicle request");
				M.addAttribute("vehicledb", request);
				M.addAttribute("vlist", cBranch.getAllVehicles());
				return "bReqNewVeh";
			}
		}
		catch(Exception E) {
			M.addAttribute("msg", "Error. Contact system admin for further assistence.");
			return requestPage(request, M);
		}
	}
	
}
