package com.techwave.client.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
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
import com.techwave.client.model.Customerdb;
import com.techwave.client.model.Logindb;
import com.techwave.client.model.Vehicledb;

@Controller
@SessionAttributes({"logindb", "vehicles[]"})
public class CustomerController {
	
	@Autowired
	Client cCustomer;
	
	@RequestMapping("OnlineVehicleBookingSystem/CustomerHomepage")
	public String custPage(@SessionAttribute("logindb") Logindb L, Customerdb C, Model M) {
		if(cCustomer.getByCustId(L.getUserId())!=null) {
			M.addAttribute("display", false);
			M.addAttribute("customerdb",cCustomer.getByCustId(L.getUserId()));
			return "cHome";
		}
		else {
			M.addAttribute("display", true);
			M.addAttribute("customerdb", new Customerdb());
			return "cHome";
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/CustomerHomepage/SaveInfo")
	public String custSave(@SessionAttribute("logindb") Logindb L,@Valid @ModelAttribute("customerdb") Customerdb C, BindingResult bs, Model M) {
		try {
			if(bs.hasErrors()) {
				M.addAttribute("logindb", L);
				M.addAttribute("customerdb", new Customerdb());
				M.addAttribute("display", true);
				M.addAttribute("msg", "Error in saving your info. Contact System Administrator");
				return "register";
			}
			else {
				C.setcustId(L);
				String msg = cCustomer.validateCustInfo(C);
				if(msg.equals("Valid Credentials")) {
					cCustomer.AddCust(C);
					M.addAttribute("msg", msg);
					M.addAttribute("display", false);
					M.addAttribute("customerdb", C);
					return "cHome";
				}
				else {
					M.addAttribute("display", true);
					M.addAttribute("msg", msg);
					M.addAttribute("customerdb", new Customerdb());
					return "cHome";
				}
			}
		}
		catch(Exception E) {
			M.addAttribute("logindb", L);
			M.addAttribute("customerdb", new Customerdb());
			M.addAttribute("display", true);
			M.addAttribute("msg", "Error in saving your info. Contact System Administrator");
			return "cHome";
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/VehicleSearch")
	public String vehicleSearch(Model M) {
		List<String> branchIds = cCustomer.getAllBranchIds();
		if(branchIds!=null) {
			M.addAttribute("branchIds", branchIds);
		}
		else {
			M.addAttribute("branchIds", new ArrayList<String>());
		}
		M.addAttribute("vehicledb", new Vehicledb());
		M.addAttribute("vlist", new ArrayList<Vehicledb>());
		return "cVehSearch";
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/VehicleSearch/Display")
	public String vehicleDisplay(@Valid @ModelAttribute("vehicledb") Vehicledb search, BindingResult bs, @RequestParam("price1") String price1, @RequestParam("price2") String price2, Model M) {
		try {
			if(bs.hasErrors()) {
				M.addAttribute("msg", bs.getAllErrors());
				return vehicleSearch(M);
			} else if (!bs.hasErrors()) {
				List<Vehicledb> vlist;
				if(price1=="" || price2=="" || 
				Integer.parseInt(price1)>Integer.parseInt(price2) 
				|| Integer.parseInt(price1) < 0 
				|| Integer.parseInt(price2) < 0) {
					throw new NumberFormatException();
				} else {
					int p1 = Integer.parseInt(price1);
					int p2 = Integer.parseInt(price2);
					vlist = cCustomer.searchVehicles(search, p1, p2);
				}
				if(vlist!=null) {
					M.addAttribute("vlist", vlist);
				} else {
					M.addAttribute("vlist", new ArrayList<Vehicledb>());
				}
				List<String> branchIds = cCustomer.getAllBranchIds();
				if(branchIds!=null) {
					M.addAttribute("branchIds", branchIds);
				} else {
					M.addAttribute("branchIds", new ArrayList<Branchdb>());
				}
				M.addAttribute("vehicledb", search);
				M.addAttribute("price1", price1);
				M.addAttribute("price2", price2);
				System.out.println("no bs error");
				return "cVehSearch";
			} else {
				throw new Exception();
			}
		} catch(Exception E) {
			M.addAttribute("msg", "Prices cannot be empty. Price 1 must be lower than Price 2. Both prices cannot be less than $0.");
			return vehicleSearch(M);
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/VehicleSearch/Book")
	public String vehicleBooked(@Valid @ModelAttribute("vehicledb") Vehicledb search, BindingResult bs, @ModelAttribute("logindb") Logindb L,  @RequestParam("vehicles[]") String[] vehicles ,Model M) {
		try {
			if(bs.hasErrors()) {
				M.addAttribute("msg", "BindingResult");
				System.out.println("bs error in book");
				return vehicleDisplay(search, bs, null, null, M);
			}
			else if (!bs.hasErrors()) {
				List<Vehicledb> vlist = new ArrayList<Vehicledb>();
				for(String s : vehicles) {
					Vehicledb v = cCustomer.getByVehicleId(s);
					Random ran = new Random();
					int digit1 = ran.nextInt(0, 10);
					int digit2 = ran.nextInt(0, 10);
					int digit3 = ran.nextInt(0, 10);
					String bookingId = "b"+digit1+""+digit2+""+digit3;
					CustomerBooking cb = new CustomerBooking(bookingId,  cCustomer.getByCustId(L.getUserId()), v, v.getbranchId(), "pending");
					cCustomer.AddBooking(cb);
					vlist.add(v);
				}
				List<String> branchIds = cCustomer.getAllBranchIds();
				if(branchIds!=null) {
					M.addAttribute("branchIds", branchIds);
				}
				else {
					M.addAttribute("branchIds", null);
				}
				M.addAttribute("vlist", vlist);
				M.addAttribute("vehicledb", search);
				M.addAttribute("msg2", "Vehicles have been booked");
				// System.out.println("no error in book");
				return "cVehSearch";
			}
			else {
				throw new Exception();
			}
		}
		catch(Exception E) {		
			M.addAttribute("msg", "Exception");
			System.out.println("exception error in book");
			System.out.println(E.getLocalizedMessage());
			return vehicleDisplay(search, bs, null, null, M);
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/VehicleBooking")
	public String bookingPage(@SessionAttribute("logindb") Logindb L, Model M) {
		try {
			List<CustomerBooking> cbList;
			cbList = cCustomer.getAllBookings().stream().filter(i->i.getCustId().getcustId().getUserId().equals(L.getUserId())).collect(Collectors.toList());
			if(cbList!=null) {
				M.addAttribute("cbList", cbList);
				return "cVehBook";
			}
			else {
				throw new Exception();
			}
		}
		catch(Exception E) {
			M.addAttribute("cbList", null);
			M.addAttribute("msg", "No bookings available.");
			return "cVehBook";
		}
	}
}
