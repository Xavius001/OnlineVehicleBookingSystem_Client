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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.techwave.client.model.Customerdb;
import com.techwave.client.model.Logindb;

@SpringBootTest
public class TestClientCustomer {
    
    @Mock 
    private RestTemplate restTemplate;

    @InjectMocks
    private ClientCustomer clientCustomer;

    @Mock
    private static Customerdb C;

    @Mock
    private static Logindb L;

    private List<Customerdb> customers;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        
        clientCustomer = new ClientCustomer();
        clientCustomer.restTemplate = restTemplate;
        clientCustomer.url = "http://localhost:8081/";
        
        L = new Logindb();
        L.setUserId("Test@test.com");
        L.setPassword("Testing@123");
        L.setRole("customer");
        L.setStatus("");
        
        C = new Customerdb();
        C.setcustId(L);
        C.setName("Tester");
        C.setDob(null);
        C.setAddress("Test Address");
        C.setPno("1234567890");
        C.setOccupation("Unit Tester");
        customers = new ArrayList<>();
        customers.add(C);
    }

    @Test
    void testGetAllCust() {
        when(restTemplate.getForObject(clientCustomer.url+"getAllCust", Customerdb[].class)).thenReturn(customers.toArray(new Customerdb[0]));
        assertEquals(customers, clientCustomer.getAllCust());
    }

    @Test
    void testGetByCustId() {
        when(restTemplate.getForObject(clientCustomer.url+"getByCustId/"+C.getcustId().getUserId(), Customerdb.class)).thenReturn(C);
        assertEquals(C, clientCustomer.getByCustId(C.getcustId().getUserId()));
    }

    @Test
    void testAddCust() {
        when(restTemplate.postForObject(clientCustomer.url+"AddCust", C, String.class)).thenReturn("Customer added");
        assertEquals("Customer added", clientCustomer.AddCust(C));
    }

    @Test
    void testDeleteCust() {
        when(restTemplate.exchange(
            clientCustomer.url + "DeleteCust/" + C.getcustId().getUserId(),
            HttpMethod.DELETE,
            new HttpEntity<>(C),
            String.class
        )).thenReturn(ResponseEntity.ok("Customer Deleted."));
        assertEquals("Customer Deleted.", clientCustomer.DeleteCust(C));
    }

    @Test
    void testValidateCustInfo() {
        when(restTemplate.postForObject(clientCustomer.url+"validateCustInfo", C, String.class)).thenReturn("Customer validated.");
        assertEquals("Customer validated.", clientCustomer.validateCustInfo(C));
    }

}
