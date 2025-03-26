package com.techwave.client.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.techwave.client.model.Branchdb;
import com.techwave.client.model.CustomerBooking;
import com.techwave.client.model.Customerdb;
import com.techwave.client.model.Logindb;
import com.techwave.client.model.Vehicledb;

@SpringBootTest
public class TestClientBooking {
    
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private ClientBooking clientBooking;

    @Mock
    private static CustomerBooking cb;

    @Mock
    private static Branchdb B;

    @Mock
    private static Logindb L;

    @Mock
    private static Customerdb C;

    @Mock
    private static Vehicledb V;

    List<CustomerBooking> bookings;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        clientBooking = new ClientBooking();
        clientBooking.restTemplate = restTemplate; // Inject mocked RestTemplate
        clientBooking.url = "http://localhost:8081/"; // Set base URL
        
        L = new Logindb();
        L.setUserId("Test@test.com");
        L.setPassword("Testing@123");
        L.setRole("customer");
        L.setStatus("");

        B = new Branchdb();
        B.setbranchId(L);
        B.setbLoc("Branch Location");
        B.setAddress("Specific Location");
        B.setPno("123-4567890");
        
        C = new Customerdb();
        C.setcustId(L);
        C.setName("Tester");
        C.setDob(null);
        C.setAddress("Test Address");
        C.setPno("1234567890");
        C.setOccupation("Unit Tester");
        
        V = new Vehicledb();
        V.setColor("Red");
        V.setManufactureName("Toyota");
        V.setPrice(10000);
        V.setSeatingCapacity(5);
        V.setVehicleId("1");
        V.setStatus("approved");
        V.setStock(10);
        V.setbranchId(B);

        cb = new CustomerBooking();
        cb.setBookingId("1");   
        cb.setCustId(C);
        cb.setVehicleId(V);
        cb.setStatus("booked");
        cb.setBranchId(B);
        
        bookings = new ArrayList<>();
        bookings.add(cb);
    }

    @Test
    void testGetAllBookings() {
        when(restTemplate.getForObject(
            clientBooking.url + "getAllBookings", 
            CustomerBooking[].class
        )).thenReturn(bookings.toArray(new CustomerBooking[0]));
        assertEquals(bookings, clientBooking.getAllBookings());
    }

    @Test
    void testGetByBookingId() {
        when(restTemplate.getForObject(
            clientBooking.url+"getByBookingId/"+cb.getBookingId(), 
            CustomerBooking.class
        )).thenReturn(cb);
        assertEquals(cb, clientBooking.getByBookingId(cb.getBookingId()));
    }

    @Test
    void testAddBooking() {
        when(restTemplate.postForObject(
            clientBooking.url+"AddBooking", 
            cb, 
            String.class
        )).thenReturn("Booking Added");
        assertEquals("Booking Added", clientBooking.AddBooking(cb));
    }

    
}