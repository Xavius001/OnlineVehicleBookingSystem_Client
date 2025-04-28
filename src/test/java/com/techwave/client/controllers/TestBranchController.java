package com.techwave.client.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.techwave.client.dao.Client;
import com.techwave.client.model.Branchdb;
import com.techwave.client.model.CustomerBooking;
import com.techwave.client.model.Logindb;
import com.techwave.client.model.Vehicledb;

@WebMvcTest(BranchController.class)
public class TestBranchController {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Client cBranch;

    @Mock
    private Logindb L;

    @Mock
    private Branchdb B;

    @BeforeEach
    void setup() {
        L = new Logindb();
        L.setUserId("Test@test.com");
        L.setPassword("Testing@123");
        L.setRole("branch");
        L.setStatus("");

        B = new Branchdb();
        B.setbranchId(L);
        B.setbLoc("Branch Location");
        B.setAddress("Specific Location");
        B.setPno("123-4567890");

    }

    @Test
    void testBranchPage() throws Exception {
        when(cBranch.getByBranchId(L.getUserId())).thenReturn(B);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/BranchHomepage")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("display", "branchdb"))
                .andExpect(status().isOk())
                .andExpect(view().name("bHome"));
    }

    @Test
    void testBranchPageNull() throws Exception {
        when(cBranch.getByBranchId(L.getUserId())).thenReturn(null);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/BranchHomepage")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("display", "branchdb"))
                .andExpect(status().isOk())
                .andExpect(view().name("bHome"));
    }

    @Test
    void testBranchSave() throws Exception {
        when(cBranch.validateBranchInfo(any(Branchdb.class))).thenReturn("Valid Credentials");
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/BranchHomepage/SaveInfo")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("msg", "display", "branchdb"))
                .andExpect(status().isOk())
                .andExpect(view().name("bHome"));
    }

    @Test
    void testBranchSaveElse() throws Exception {
        when(cBranch.validateBranchInfo(any(Branchdb.class))).thenReturn("Invalid Credentials");
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/BranchHomepage/SaveInfo")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("msg", "display", "branchdb"))
                .andExpect(status().isOk())
                .andExpect(view().name("bHome"));
    }

    @Test
    void testBranchSaveThrow() throws Exception {
        when(cBranch.validateBranchInfo(any(Branchdb.class))).thenThrow(new RuntimeException("Mocked exception"));
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/BranchHomepage/SaveInfo")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("logindb", "msg", "display", "branchdb"))
                .andExpect(status().isOk())
                .andExpect(view().name("bHome"));
    }

    @Test
    void testApprovePage() throws Exception {
        when(cBranch.getAllBookings()).thenReturn(new ArrayList<CustomerBooking>());
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/ApproveVehicleBooking")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("cbList"))
                .andExpect(status().isOk())
                .andExpect(view().name("bAprVehBook"));
    }

    @Test
    void testApprovePageCatch() throws Exception {
        when(cBranch.getAllBookings()).thenReturn(null);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/ApproveVehicleBooking")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("msg"))
                .andExpect(status().isOk())
                .andExpect(view().name("bAprVehBook"));
    }

    @Test
    void testApproveBookingPage() throws Exception {
        CustomerBooking cb = new CustomerBooking();
        cb.setBookingId("booking1");
        when(cBranch.getByBookingId(anyString())).thenReturn(cb);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/ApproveVehicleBooking/ApproveBooking")
        .sessionAttr("logindb", L)
        .param("bookings[]", "booking1"))
                .andExpect(model().attributeExists("cbList"))
                .andExpect(status().isOk())
                .andExpect(view().name("bAprVehBook"));
    }

    @Test
    void testApproveBookingPageCatch() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/ApproveVehicleBooking/ApproveBooking")
        .sessionAttr("logindb", L)
        .param("bookings[]",  (String) null))
                .andExpect(model().attributeExists("cbList", "msg"))
                .andExpect(status().isOk())
                .andExpect(view().name("bAprVehBook"));
    }

    @Test
    void testRejectBookingPage() throws Exception {
        CustomerBooking cb = new CustomerBooking();
        cb.setBookingId("booking1");
        when(cBranch.getByBookingId(anyString())).thenReturn(cb);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/ApproveVehicleBooking/RejectBooking")
        .sessionAttr("logindb", L)
        .param("bookings[]", "booking1"))
                .andExpect(model().attributeExists("cbList"))
                .andExpect(status().isOk())
                .andExpect(view().name("bAprVehBook"));
    }

    @Test
    void testRejectBookingPageCatch() throws Exception {
        CustomerBooking cb = new CustomerBooking();
        cb.setBookingId("booking1");
        when(cBranch.getByBookingId(anyString())).thenReturn(cb);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/ApproveVehicleBooking/RejectBooking")
        .sessionAttr("logindb", L)
        .param("bookings[]",   (String) null))
                .andExpect(model().attributeExists("cbList", "msg"))
                .andExpect(status().isOk())
                .andExpect(view().name("bAprVehBook"));
    }

    @Test
    void testRequestPage() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/RequestNewVehicles")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("vehicledb"))
                .andExpect(status().isOk())
                .andExpect(view().name("bReqNewVeh"));
    }

    @Test
    void testRequestPage2() throws Exception {
        when(cBranch.requestVehicleStock(any(Vehicledb.class))).thenReturn("Vehicle requested successfully.");
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/RequestNewVehicles/Request")
        .sessionAttr("logindb", L)
        .flashAttr("vehicledb", new Vehicledb("vehicle1", "toyota", "blue", 5, 20000, B, 10, "approved", 0)))
                .andExpect(model().attributeExists("vehicledb", "msg", "vlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("bReqNewVeh"));
    }

}
