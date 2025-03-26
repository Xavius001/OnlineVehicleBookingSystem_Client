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

import com.techwave.client.model.Logindb;

@SpringBootTest
public class TestClientLogin {
    
    @Mock 
    private RestTemplate restTemplate;

    @InjectMocks
    private ClientLogin clientLogin;

    @Mock
    private static Logindb L;

    private List<Logindb> logins;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        clientLogin = new ClientLogin();
        clientLogin.restTemplate = restTemplate;
        clientLogin.url = "http://localhost:8081/";

        L = new Logindb();
        L.setUserId("Test@test.com");
        L.setPassword("Testing@123");
        L.setRole("customer");
        L.setStatus("");

        logins = new ArrayList<Logindb>();
        logins.add(L);
    }

    @Test
    void testGetAllLogins() {
        when(restTemplate.getForObject(clientLogin.url + "getAllLogins", Logindb[].class)).thenReturn(logins.toArray(new Logindb[0]));
        assertEquals(logins, clientLogin.getAllLogins());
    }

    @Test
    void testGetByLoginId() {
        when(restTemplate.getForObject(clientLogin.url + "getByLoginId/" + L.getUserId(), Logindb.class)).thenReturn(L);
        assertEquals(L, clientLogin.getByLoginId(L.getUserId()));
    }

    @Test
    void testAddLogin() {
        when(restTemplate.postForObject(clientLogin.url + "AddLogin", L, String.class)).thenReturn("Login added");
        assertEquals("Login added", clientLogin.AddLogin(L));
    }

    @Test
    void testUpdateLogin() {
        when(restTemplate.exchange(
            clientLogin.url + "UpdateLogin/" + L.getUserId(), 
            HttpMethod.PUT, 
            new HttpEntity<>(L), 
            String.class
        )).thenReturn(ResponseEntity.ok("Login updated"));
        assertEquals("Login updated", clientLogin.UpdateLogin(L, L.getUserId()));
    }

    @Test
    void testApproveLogin() {
        when(restTemplate.exchange(
            clientLogin.url + "ApproveLogin/" + L.getUserId(),
            HttpMethod.PUT,
            new HttpEntity<>(L),
            String.class
        )).thenReturn(ResponseEntity.ok("Login approved"));
        assertEquals("Login approved", clientLogin.ApproveLogin(L, L.getUserId()));
    }

    @Test
    void testRejectLogin() {
        when(restTemplate.exchange(
            clientLogin.url + "RejectLogin/" + L.getUserId(),
            HttpMethod.PUT,
            new HttpEntity<>(L),
            String.class
        )).thenReturn(ResponseEntity.ok("Login rejected"));
        assertEquals("Login rejected", clientLogin.RejectLogin(L, L.getUserId()));
    }

    @Test
    void testDeleteLogin() {
        when(restTemplate.exchange(
            clientLogin.url + "DeleteLogin/" + L.getUserId(),
            HttpMethod.DELETE,
            new HttpEntity<>(L),
            String.class
        )).thenReturn(ResponseEntity.ok("Login deleted."));
        assertEquals("Login deleted.", clientLogin.DeleteLogin(L));
    }

    @Test
    void testVerifyEmail() {
        when(clientLogin.getByLoginId(L.getUserId())).thenReturn(L);
        when(restTemplate.exchange(
			clientLogin.url + "verify/email=" + L.getUserId(), // URL with vehicle ID
			HttpMethod.PUT,                            // HTTP method
			new HttpEntity<>(L),                       // Request body wrapped in HttpEntity
			String.class                               // Expected response type
    	)).thenReturn(ResponseEntity.ok("Email verified"));
        assertEquals("Email verified", clientLogin.verifyEmail(L.getUserId()));
    }

    @Test
    void testValidateLogin() {
        when(restTemplate.getForObject(clientLogin.url+"ValidateLogin/"+L.getUserId(), String.class)).thenReturn("Login validated");
        assertEquals("Login validated", clientLogin.validateLogin(L));
    }

    @Test
    void testNewUserRequest() {
        when(restTemplate.postForObject(clientLogin.url+"NewUserRequest", L, String.class)).thenReturn("New user request added");
        assertEquals("New user request added", clientLogin.newUserRequest(L));
    }


}
