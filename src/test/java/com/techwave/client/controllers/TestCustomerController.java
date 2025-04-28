package com.techwave.client.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.validation.BindingResult;

import com.techwave.client.dao.Client;
import com.techwave.client.model.Branchdb;
import com.techwave.client.model.CustomerBooking;
import com.techwave.client.model.Customerdb;
import com.techwave.client.model.Logindb;
import com.techwave.client.model.Vehicledb;

@WebMvcTest(CustomerController.class)
public class TestCustomerController {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Client cCustomer;

    @Mock
    private Logindb L;

    @Mock
    private Customerdb C;

    @Mock
    private Branchdb B;

    @BeforeEach
    void setup() {
        L = new Logindb();
        L.setUserId("Test@test.com");
        L.setPassword("Testing@123");
        L.setRole("customer");
        L.setStatus("approved");

        C = new Customerdb();
        C.setcustId(L);
        C.setName("Tester");
        C.setDob(null);
        C.setAddress("Test Address");
        C.setPno("1234567890");
        C.setOccupation("Unit Tester");

        B = new Branchdb();
        B.setbranchId(L);
        B.setbLoc("Branch Location");
        B.setAddress("Specific Location");
        B.setPno("123-4567890");

    }

    @Test
    void testCustPage() throws Exception {
        when(cCustomer.getByCustId(L.getUserId())).thenReturn(C);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/CustomerHomepage")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("display", "customerdb"))
                .andExpect(status().isOk())
                .andExpect(view().name("cHome"));
    }

    @Test
    void testCustPageNull() throws Exception {
        when(cCustomer.getByCustId(L.getUserId())).thenReturn(null);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/CustomerHomepage")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("display", "customerdb"))
                .andExpect(status().isOk())
                .andExpect(view().name("cHome"));
    }

    @Test
    void testCustSave() throws Exception {
        when(cCustomer.validateCustInfo(any(Customerdb.class))).thenReturn("Valid Credentials");
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/CustomerHomepage/SaveInfo")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("display", "customerdb","msg"))
                .andExpect(status().isOk())
                .andExpect(view().name("cHome"));
    }

    @Test
    void testCustSaveInvalid() throws Exception {
        when(cCustomer.validateCustInfo(any(Customerdb.class))).thenReturn("Invalid Credentials");
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/CustomerHomepage/SaveInfo")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("display", "customerdb","msg"))
                .andExpect(status().isOk())
                .andExpect(view().name("cHome"));
    }

    @Test
    void testVehicleSearch() throws Exception {
        when(cCustomer.getAllBranchIds()).thenReturn(new ArrayList<String>());
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/VehicleSearch")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("branchIds","vehicledb","vlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("cVehSearch"));
    }

    @Test
    void testVehicleSearchNull() throws Exception {
        when(cCustomer.getAllBranchIds()).thenReturn(null);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/VehicleSearch")
        .sessionAttr("logindb", L))
                .andExpect(model().attributeExists("vehicledb","vlist"))
                .andExpect(status().isOk())
                .andExpect(view().name("cVehSearch"));
    }

    // @Test
    // void testVehicleDisplay() throws Exception {
    //     when(cCustomer.searchVehicles(any(Vehicledb.class), anyInt(), anyInt())).thenReturn(new ArrayList<Vehicledb>());
    //     when(cCustomer.getAllBranchIds()).thenReturn(new ArrayList<String>());
    //     this.mockMvc.perform(get("/OnlineVehicleBookingSystem/VehicleSearch/Display")
    //     .sessionAttr("logindb", L)
    //     .param("price1","1")
    //     .param("price2","10000"))
    //             .andExpect(model().attributeExists("branchIds","vehicledb","vlist"))
    //             .andExpect(status().isOk())
    //             .andExpect(view().name("cVehSearch"));
    // }

    // @Test
    // void testVehicleDisplayException() throws Exception {
    //     when(cCustomer.searchVehicles(any(Vehicledb.class), anyInt(), anyInt())).thenReturn(new ArrayList<Vehicledb>());
    //     when(cCustomer.getAllBranchIds()).thenReturn(new ArrayList<String>());
    //     this.mockMvc.perform(get("/OnlineVehicleBookingSystem/VehicleSearch/Display")
    //     .sessionAttr("logindb", L)
    //     .param("price1","")
    //     .param("price2", ""))
    //         .andExpect(model().attributeExists("branchIds", "vehicledb", "vlist"))
    //         .andExpect(status().isOk())
    //         .andExpect(view().name("cVehSearch"));
    // }

    // @Test
    // void testVehicleDisplayNull() throws Exception {
    //     when(cCustomer.searchVehicles(any(Vehicledb.class), anyInt(), anyInt())).thenReturn(null);
    //     when(cCustomer.getAllBranchIds()).thenReturn(null);
    //     this.mockMvc.perform(get("/OnlineVehicleBookingSystem/VehicleSearch/Display")
    //     .sessionAttr("logindb", L)
    //     .param("price1","1")
    //     .param("price2", "10000"))
    //         .andExpect(model().attributeExists("branchIds", "vehicledb", "vlist"))
    //         .andExpect(status().isOk())
    //         .andExpect(view().name("cVehSearch"));
    // }

    @Test
    void testVehicleBooked() throws Exception {
        when(cCustomer.getByVehicleId(anyString())).thenReturn(new Vehicledb("vehicle1", "Toyota", "Blue", 5, 20000, B, 10, "approved", 0));
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/VehicleSearch/Book")
        .sessionAttr("logindb", L)
        .flashAttr("vehicledb", new Vehicledb("vehicle1", "Toyota", "Blue", 5, 20000, B, 10, "approved", 0))
        .param("vehicles[]", "vehicle1")
        )
            .andExpect(model().attributeExists("branchIds", "vehicledb", "vlist"))
            .andExpect(status().isOk())
            .andExpect(view().name("cVehSearch"));
    }

    @Test
    void testVehicleBookedNull() throws Exception {
        when(cCustomer.getByVehicleId(anyString())).thenReturn(new Vehicledb("vehicle1", "Toyota", "Blue", 5, 20000, B, 10, "approved", 0));
        when(cCustomer.getAllBranchIds()).thenReturn(null);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/VehicleSearch/Book")
        .sessionAttr("logindb", L)
        .flashAttr("vehicledb", new Vehicledb("vehicle1", "Toyota", "Blue", 5, 20000, B, 10, "approved", 0))
        .param("vehicles[]", "vehicle1")
        )
            .andExpect(model().attributeExists( "vehicledb", "vlist"))
            .andExpect(status().isOk())
            .andExpect(view().name("cVehSearch"));
    }
    
    @Test
    void testVehicleBookedCatch() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/VehicleSearch/Book")
        .sessionAttr("logindb", L)
        .flashAttr("vehicledb", new Vehicledb("vehicle1", "Toyota", "Blue", 5, 20000, null, 10, "approved", 0))
        .param("vehicles[]", "vehicle1")
        )
            .andExpect(model().attributeExists("branchIds", "vehicledb", "vlist"))
            .andExpect(status().isOk())
            .andExpect(view().name("cVehSearch"));
    }

    @Test
    void testBookingPage() throws Exception {
        when(cCustomer.getAllBookings()).thenReturn(new ArrayList<CustomerBooking>());
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/VehicleBooking")
        .sessionAttr("logindb", L)
        )
            .andExpect(model().attributeExists( "cbList"))
            .andExpect(status().isOk())
            .andExpect(view().name("cVehBook"));
    }

    @Test
    void testBookingPageCatch() throws Exception {
        when(cCustomer.getAllBookings()).thenReturn(null);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/VehicleBooking")
        .sessionAttr("logindb", L)
        )
            .andExpect(model().attributeExists( "msg"))
            .andExpect(status().isOk())
            .andExpect(view().name("cVehBook"));
    }

}
