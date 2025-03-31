package com.techwave.client.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.techwave.client.dao.Client;
import com.techwave.client.model.Branchdb;
import com.techwave.client.model.Logindb;

@WebMvcTest(LoginController.class)
public class TestLoginController {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Client cLogin;

    @Mock
    private Logindb L;

    @BeforeEach
    void setup() {
        L = new Logindb();
        L.setUserId("Test@test.com");
        L.setPassword("Testing@123");
        L.setRole("customer");
        L.setStatus("");
    }

    @Test
    void testHomePage() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem"))
            .andExpect(status().isOk())
            .andExpect(view().name("home"));
    }

    @Test
    void testRegisterPage() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/register"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("logindb"))
            .andExpect(view().name("register"));
    }

    @Test
    void testRegisteredPage() throws Exception {
        when(cLogin.newUserRequest(any(Logindb.class))).thenReturn("User Created Successfully");
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/registered")
        .sessionAttr("logindb", L))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("logindb","msg"))
            .andExpect(view().name("register"));
    }

    @Test
    void testRegisteredPageBindingError() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/registered")
        .sessionAttr("logindb", new Logindb()))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("logindb","msg"))
            .andExpect(view().name("register"));
    }

    @Test
    void testLoginPage() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/login"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("logindb"))
            .andExpect(view().name("login"));
    }

    @Test
    void testVerifyPage() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/verify")
        .param("email", L.getUserId()))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("email"))
            .andExpect(view().name("verify"));
    }

    @Test
    void testVerifiedPage() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/verified")
        .param("email", L.getUserId()))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("email"))
            .andExpect(view().name("verify"));
    }

    @Test
    void testValidateBindingError() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/Validatelogin")
        .sessionAttr("logindb", new Logindb()))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("msg","logindb"))
            .andExpect(view().name("login"));
    }
    
    @Test
    void testValidateInvalidRole() throws Exception {
        when(cLogin.validateLogin(any(Logindb.class))).thenReturn("Invalid");
        when(cLogin.getLoginStatus(any(Logindb.class))).thenReturn("Invalid");
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/Validatelogin")
        .sessionAttr("logindb", L))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("msg","logindb"))
            .andExpect(view().name("login"));
    }

    @Test
    void testValidateLoginCustomer() throws Exception {
        when(cLogin.validateLogin(any(Logindb.class))).thenReturn("customer");
        when(cLogin.getLoginStatus(any(Logindb.class))).thenReturn("approved");
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/Validatelogin")
        .sessionAttr("logindb", L))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/OnlineVehicleBookingSystem/CustomerHomepage"));
    }

    @Test
    void testValidateLoginBranch() throws Exception {
        when(cLogin.validateLogin(any(Logindb.class))).thenReturn("branch");
        when(cLogin.getLoginStatus(any(Logindb.class))).thenReturn("approved");
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/Validatelogin")
        .sessionAttr("logindb", L))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/OnlineVehicleBookingSystem/BranchHomepage"));
    }

    @Test
    void testValidateLoginAdmin() throws Exception {
        when(cLogin.validateLogin(any(Logindb.class))).thenReturn("admin");
        when(cLogin.getLoginStatus(any(Logindb.class))).thenReturn("approved");
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/Validatelogin")
        .sessionAttr("logindb", L))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/OnlineVehicleBookingSystem/AdminHomepage"));
    }

    @Test
    void testLogoutPage() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/logout"))
            .andExpect(status().isFound())
            .andExpect(view().name("redirect:/OnlineVehicleBookingSystem/login"));
    }

}
