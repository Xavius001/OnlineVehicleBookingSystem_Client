package com.techwave.client.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.techwave.client.model.CustomerBooking;

public class ClientBooking extends ClientBranch implements IClientBooking {
	
	@Autowired
	RestTemplate restTemplate;
	public String url="http://localhost:8081/"; //server
	
	@Override
	public List<CustomerBooking> getAllBookings() {
		CustomerBooking list[] = restTemplate.getForObject(url+"getAllBookings", CustomerBooking[].class);
		return Arrays.asList(list);
	}
	@Override
	public CustomerBooking getByBookingId(String bookingId) {
		return restTemplate.getForObject(url+"getByBookingId/"+bookingId, CustomerBooking.class);
	}
	@Override
	public String AddBooking(CustomerBooking cb) {
		return restTemplate.postForObject(url+"AddBooking", cb, String.class);
	}
	
	// REDO THE PUTS AND DELETE FOR THE METHODS BELOW (homework for tonight)
	@Override
	public String UpdateBooking(CustomerBooking cb) {
		// restTemplate.put(url+"UpdateBooking/"+cb.getBookingId(), String.class);
		return "Booking updated";
	}
	@Override
	public String DeleteBooking(CustomerBooking cb) {
		// restTemplate.delete(url+"DeleteBooking/"+cb.getBookingId());
		return "Booking option deleted";
	}
	@Override
	public String ApproveBooking(CustomerBooking cb, String bookingId) {
		// restTemplate.put(url+"ApproveBooking/"+bookingId, cb);
		return "Approved";
	}
	
	@Override
	public String RejectBooking(CustomerBooking cb, String bookingId) {
		// restTemplate.put(url+"RejectBooking/"+bookingId, cb);
		return "Rejected";
	}
}
