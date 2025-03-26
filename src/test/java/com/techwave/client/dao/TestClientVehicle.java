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

import com.techwave.client.model.Branchdb;
import com.techwave.client.model.Logindb;
import com.techwave.client.model.Vehicledb;

@SpringBootTest
public class TestClientVehicle {
    
    @Mock 
    private RestTemplate restTemplate;

    @InjectMocks
    private ClientVehicle clientVehicle;

    @Mock
    private static Vehicledb V;

    @Mock
    private static Branchdb B;

    @Mock
    private static Logindb L;

    List<Vehicledb> vehicles;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        clientVehicle = new ClientVehicle();
        clientVehicle.restTemplate = restTemplate; // Inject mocked RestTemplate
        clientVehicle.url = "http://localhost:8081/"; // Set base URL
        
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

        V = new Vehicledb();
        V.setColor("Red");
        V.setManufactureName("Toyota");
        V.setPrice(10000);
        V.setSeatingCapacity(5);
        V.setVehicleId("1");
        V.setStatus("approved");
        V.setStock(10);
        V.setbranchId(B);

        vehicles = new ArrayList<>();
        vehicles.add(V);
    }

    @Test
    void testGetAllVehicles() {
        when(restTemplate.getForObject(clientVehicle.url+"getAllVehicles", Vehicledb[].class)).thenReturn(vehicles.toArray(new Vehicledb[0]));
        assertEquals(vehicles, clientVehicle.getAllVehicles());
    }

    @Test
    void testGetByVehicleId() {
        when(restTemplate.getForObject(clientVehicle.url+"getByVehicleId/"+V.getVehicleId(), Vehicledb.class)).thenReturn(V);
        assertEquals(V, clientVehicle.getByVehicleId("1"));
    }

    @Test
    void testAddVehicle() {
        when(restTemplate.postForObject(clientVehicle.url+"AddVehicle", V, String.class)).thenReturn("New Vehicle Added.");
        assertEquals("New Vehicle Added.", clientVehicle.AddVehicle(V));
    }

    @Test
    void testUpdateVehicle() {
        when(restTemplate.exchange(
            clientVehicle.url + "UpdateVehicle/" + V.getVehicleId(),
            HttpMethod.PUT,
            new HttpEntity<>(V),
            String.class
        )).thenReturn(ResponseEntity.ok("Vehicle Updated."));
        assertEquals("Vehicle Updated.", clientVehicle.UpdateVehicle(V));
    }

    @Test
    void testDeleteVehicle() {
        when(restTemplate.exchange(
            clientVehicle.url + "DeleteVehicle/" + V.getVehicleId(),
            HttpMethod.DELETE,
            new HttpEntity<>(V),
            String.class
        )).thenReturn(ResponseEntity.ok("Vehicle Deleted."));
        assertEquals("Vehicle Deleted.", clientVehicle.DeleteVehicle(V));
    }

    @Test
    void testSearchVehicles() {
        when(restTemplate.getForObject(clientVehicle.url+"SearchVehicles/1000/2000", Vehicledb[].class)).thenReturn(vehicles.toArray(new Vehicledb[0]));
        assertEquals(vehicles, clientVehicle.searchVehicles(V, 1000, 2000));
    }

    @Test
    void testRequestVehicle() {
        when(restTemplate.postForObject(clientVehicle.url+"RequestVehicle", V, String.class)).thenReturn("Vehicle Requested.");
        assertEquals("Vehicle Requested.", clientVehicle.requestVehicle(V));
    }

    @Test
    void testDisplayRequests() {
        when(restTemplate.getForObject(clientVehicle.url+"DisplayRequests", Vehicledb[].class)).thenReturn(vehicles.toArray(new Vehicledb[0]));
        assertEquals(vehicles, clientVehicle.displayRequests());
    }

    @Test
    void testApproveVehicle() {
        when(restTemplate.exchange(
            clientVehicle.url + "ApproveVehicle/",
            HttpMethod.PUT,
            new HttpEntity<>(V),
            String.class
        )).thenReturn(ResponseEntity.ok("Vehicle Approved."));
        assertEquals("Vehicle Approved.", clientVehicle.approveVehicle(V));
    }

    @Test
    void testRejectVehicle() {
        when(restTemplate.exchange(
            clientVehicle.url + "RejectVehicle/",
            HttpMethod.PUT,
            new HttpEntity<>(V),
            String.class
        )).thenReturn(ResponseEntity.ok("Vehicle Rejected."));
        assertEquals("Vehicle Rejected.", clientVehicle.rejectVehicle(V));
    }

}
