package com.techwave.client.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.springframework.web.bind.support.SessionStatus;

import com.techwave.client.dao.Client;
import com.techwave.client.model.Branchdb;
import com.techwave.client.model.Logindb;
import com.techwave.client.model.Vehicledb;

@Controller
public class AdminController {
	
	@Autowired
	Client cAdmin;
	
	@RequestMapping("OnlineVehicleBookingSystem/AdminHomepage")
	public String adminPage(Model M) {
		return "aHome";
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/AdminApprovalPage")
	public String approveLoginPage(Model M) {
		List<Logindb> list = cAdmin.getAllLogins().stream().filter(i->i.getStatus().equals("pending")).collect(Collectors.toList());
		M.addAttribute("loginList", list);
		return "aAprAccReq";
	}
	
	
	@RequestMapping("OnlineVehicleBookingSystem/AdminApprovalPage/All")
	public String AllRequests(Model M) {
		List<Logindb> list = cAdmin.getAllLogins();
		M.addAttribute("loginList", list);
		return "aAprAccReq";
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/AdminApprovalPage/RoleSort")
	public String SortRequestsByStatus(Model M) {
		List<Logindb> list = cAdmin.getAllLogins().stream().sorted(new RoleSort()).collect(Collectors.toList());
		M.addAttribute("loginList", list);
		return "aAprAccReq";
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/AdminApprovalPage/StatusSort")
	public String SortRequestsByRole(Model M) {
		List<Logindb> list = cAdmin.getAllLogins().stream().sorted(new StatusSort()).collect(Collectors.toList());
		M.addAttribute("loginList", list);
		return "aAprAccReq";
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/AdminApprovalPage/ApproveLogin")
	public String approveLoginPage2(@RequestParam(name="loginItem[]", required=false) String[] loginItem, Model M) {
		try {
			if(loginItem!=null) {
				for(String s : loginItem) {
					Logindb L = cAdmin.getByLoginId(s);
					String msg = cAdmin.ApproveLogin(L, s);
				}
				List<Logindb> list = cAdmin.getAllLogins();
				M.addAttribute("loginList", list);
				System.out.println("not null");
				return "aAprAccReq";
			}
			else {
				throw new Exception();
			}
		} 
		catch(Exception E) {
			M.addAttribute("msg", "Nothing was checked.");
			return approveLoginPage(M);
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/AdminApprovalPage/RejectLogin")
	public String approveLoginPage3(@RequestParam(name="loginItem[]", required=false) String[] loginItem, Model M) {
		try {
			if(loginItem!=null) {
				for(String s : loginItem) {
					Logindb L = cAdmin.getByLoginId(s);
					String msg = cAdmin.RejectLogin(L, s);
				}
				List<Logindb> list = cAdmin.getAllLogins();
				M.addAttribute("loginList", list);
				return "aAprAccReq";
			}
			else {
				throw new Exception();
			}
		} 
		catch(Exception E) {
			M.addAttribute("msg", "Nothing was checked.");
			return approveLoginPage(M);
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/ApproveNewVehicleRequest")
	public String approveRequestPage(Model M) {
		M.addAttribute("vlist", cAdmin.displayRequests());
		return "aAprVehReq";
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/ApproveNewVehicleRequest/Approve")
	public String approveRequestPage2(@RequestParam(name="requests[]", required=false) String[] requests, Model M) {
		try {
			if(requests!=null) {
				List<Vehicledb> vlist = cAdmin.displayRequests();
				System.out.println("vlist: " + vlist.toString());
				List<String> msg = new ArrayList<String>();
				System.out.println("msg: " + msg.toString());
				for(String s : requests) {
					Vehicledb v = vlist.stream().filter(i->i.getVehicleId().equals(s)).collect(Collectors.toList()).get(0);
					System.out.println("v: " + v.toString());
					msg.add(cAdmin.approveVehicleRequest(v));
					System.out.println("msg: " + msg.toString());
				}
				M.addAttribute("msg", msg);
				return approveRequestPage(M);
			} else {
				throw new Exception();
			}
		} catch (Exception E) {
			M.addAttribute("msg", "Nothing was checked.");
			return approveRequestPage(M);
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/ApproveNewVehicleRequest/Reject")
	public String approveRequestPage3(@RequestParam(name="requests[]", required=false) String[] requests, Model M) {
		try {
			if(requests!=null) {
				List<Vehicledb> vlist = cAdmin.displayRequests();
				List<String> msg = new ArrayList<String>();
				for(String s : requests) {
					Vehicledb v = vlist.stream().filter(i->i.getVehicleId().equals(s)).collect(Collectors.toList()).get(0);
					msg.add(cAdmin.rejectVehicleRequest(v));
				}
				M.addAttribute("msglist", msg);
				return approveRequestPage(M);
			} else {
				throw new Exception();
			}
		} catch (Exception E) {
			M.addAttribute("msg", "Nothing was checked.");
			return approveRequestPage(M);
		}
	}
	
	@RequestMapping("OnlineVehicleBookingSystem/AddNewVehicle")
	public String addNewVehicle(@ModelAttribute("vehicledb") Vehicledb V, BindingResult bs, Model M) {
		try {
			if(bs.hasErrors()) return "aAddNewVeh";
			else if (!bs.hasErrors()) return "aAddNewVeh";
			else throw new Exception();
		} catch (Exception E) {
			return "aAddNewVeh";
		}
	}
	
	
	@RequestMapping("OnlineVehicleBookingSystem/AddNewVehicle/Added")
	public String addNewVehicle2(@Valid @ModelAttribute("vehicledb") Vehicledb V, BindingResult bs, Model M) {
		try {
			if(bs.hasErrors()) {
				M.addAttribute("msg", bs.getAllErrors());
				return "aAddNewVeh";
			} else if (!bs.hasErrors()) {
				Vehicledb old = cAdmin.getByVehicleId(V.getVehicleId());
				if(old==null) {
					Branchdb B = cAdmin.getByBranchId(V.getbranchId().getbranchId().getUserId());
					V.setbranchId(B);
					cAdmin.AddVehicle(V);
					M.addAttribute("msg", "Vehicle added successfully");
				}
				else M.addAttribute("msg", "A vehicle with this ID already exists. Try again.");
				return "aAddNewVeh";
			} else throw new Exception();
		} catch (Exception E) {
			M.addAttribute("msg", E.getLocalizedMessage());
			return "aAddNewVeh";
		}
	}
}

class RoleSort implements Comparator<Logindb> {
    @Override
    public int compare(Logindb o1, Logindb o2) {
        return o1.getRole().compareTo(o2.getRole());
    }
}


class StatusSort implements Comparator<Logindb> {
    @Override
    public int compare(Logindb o1, Logindb o2) {
        return o1.getStatus().compareTo(o2.getStatus());
    }
}

