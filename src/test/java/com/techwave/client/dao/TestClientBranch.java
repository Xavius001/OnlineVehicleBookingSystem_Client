package com.techwave.client.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

@SpringBootTest
public class TestClientBranch {
    
    @Mock 
    private RestTemplate restTemplate;

    @InjectMocks
    private ClientBranch clientBranch;

    @Mock
    private static Branchdb B;

    @Mock
    private static Logindb L;

    private List<Branchdb> branches;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        
        clientBranch = new ClientBranch();
        clientBranch.restTemplate = restTemplate;
        clientBranch.url = "http://localhost:8081/";

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
        branches = new ArrayList<>();
        branches.add(B);
    }

    @Test
    void testGetAllBranches() {
        when(restTemplate.getForObject(clientBranch.url + "getAllBranches", Branchdb[].class)).thenReturn(branches.toArray(new Branchdb[0]));
        assertEquals(branches, clientBranch.getAllBranches());
    }

    @Test
    void testGetByBranchId() {
        when(restTemplate.getForObject(clientBranch.url + "getByBranchId/" + B.getbranchId().getUserId(), Branchdb.class)).thenReturn(B);
        assertEquals(B, clientBranch.getByBranchId(B.getbranchId().getUserId()));
    }

    @Test
    void testValidateBranchInfo() {
        when(restTemplate.postForObject(clientBranch.url + "validateBranchInfo", B, String.class)).thenReturn("Branch Validated.");
        assertEquals("Branch Validated.", clientBranch.validateBranchInfo(B));
    }

    @Test
    void testAddBranch() {
        when(restTemplate.postForObject(clientBranch.url + "AddBranch", B, String.class)).thenReturn("Branch Added.");
        assertEquals("Branch Added.", clientBranch.AddBranch(B));
    }

    @Test
    void testDeleteBranch() {
        when(restTemplate.exchange(
            clientBranch.url + "DeleteBranch/" + B.getbranchId().getUserId(),
            HttpMethod.DELETE,
            new HttpEntity<>(B),
            String.class
        )).thenReturn(ResponseEntity.ok("Branch Deleted."));
        assertEquals("Branch Deleted.",clientBranch.DeleteBranch(B));
    }

    @Test
    void testGetAllBranchIds() {
        List<String> branchIds = new ArrayList<>();
        branchIds.add(B.getbranchId().getUserId());
        when(restTemplate.getForObject(clientBranch.url+"GetAllBranchIds", String[].class)).thenReturn(branchIds.toArray(new String[0]));
        assertEquals(branchIds,clientBranch.getAllBranchIds());
    }
}
