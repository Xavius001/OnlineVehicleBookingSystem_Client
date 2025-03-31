package com.techwave.client.controllers;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.techwave.client.dao.Client;
import com.techwave.client.model.Branchdb;
import com.techwave.client.model.Logindb;
import com.techwave.client.model.Vehicledb;

@WebMvcTest(AdminController.class)
public class TestAdminController {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Client cAdmin;

    @Mock
    private Logindb L;

    @Mock
    private Branchdb B;

    @BeforeEach
    void setup() {
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

    }

    @Test
    void testAdminPage() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AdminHomepage"))
                .andExpect(status().isOk())
                .andExpect(view().name("aHome"));
    }

    @Test
    void testApproveLoginPage() throws Exception {        
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AdminApprovalPage"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loginList"))
                .andExpect(view().name("aAprAccReq"));
    }

    @Test
    void testAllRequests() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AdminApprovalPage/All"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loginList"))
                .andExpect(view().name("aAprAccReq"));
    }

    @Test
    void testSortRequestsByStatus() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AdminApprovalPage/RoleSort"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loginList"))
                .andExpect(view().name("aAprAccReq"));
    }

    @Test
    void testSortRequestsByRole() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AdminApprovalPage/StatusSort"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loginList"))
                .andExpect(view().name("aAprAccReq"));
    }

    @Test
    void testApproveLoginPage2() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AdminApprovalPage/ApproveLogin")
        .param("loginItem[]", new String[] {"login1", "login2"}))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loginList"))
                .andExpect(view().name("aAprAccReq"));
    }

    @Test
    void testApproveLoginPage2WithNull() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AdminApprovalPage/ApproveLogin")
        .param("loginItem[]", (String) null))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(view().name("aAprAccReq"));
    }

    @Test
    void testApproveLoginPage3() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AdminApprovalPage/RejectLogin")
        .param("loginItem[]", new String[] {"login1", "login2"}))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("loginList"))
                .andExpect(view().name("aAprAccReq"));
    }

    @Test
    void testApproveLoginPage3WithNull() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AdminApprovalPage/RejectLogin")
        .param("loginItem[]", (String) null))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(view().name("aAprAccReq"));
    }

    @Test
    void testApproveRequestPage() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/ApproveNewVehicleRequest"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vlist"))
                .andExpect(view().name("aAprVehReq"));
    }

    @Test
    void testApproveRequestPage2() throws Exception {
        List<Vehicledb> list = new ArrayList<>();
        Vehicledb v1 = new Vehicledb("vehicle1", "toyota", "blue", 5, 20000, B, 10, "approved");
        list.add(v1);
        when(cAdmin.displayRequests()).thenReturn(list);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/ApproveNewVehicleRequest/Approve")
        .param("requests[]", "vehicle1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vlist"))
                .andExpect(model().attributeExists("msg"))
                .andExpect(view().name("aAprVehReq"));
    }

    @Test
    void testApproveRequestPage2Catch() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/ApproveNewVehicleRequest/Approve")
        .param("requests[]", (String) null))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("vlist"))
                .andExpect(model().attributeExists("msg"))
                .andExpect(view().name("aAprVehReq"));
    }

    @Test
    void testApproveRequestPage3() throws Exception {
        List<Vehicledb> list = new ArrayList<>();
        Vehicledb v1 = new Vehicledb("vehicle1", "toyota", "blue", 5, 20000, B, 10, "approved");
        list.add(v1);
        when(cAdmin.displayRequests()).thenReturn(list);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/ApproveNewVehicleRequest/Reject")
        .param("requests[]", "vehicle1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msglist"))
                .andExpect(view().name("aAprVehReq"));
    }

    @Test
    void testApproveRequestPage3WCatch() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/ApproveNewVehicleRequest/Reject")
        .param("requests[]", (String) null))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(view().name("aAprVehReq"));
    }

    @Test
    void testAddNewVehicle() throws Exception {
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AddNewVehicle")
        .flashAttr("vehicledb", new Vehicledb("vehicle1", "toyota", "blue", 5, 20000, B, 10, "approved")))
            .andExpect(status().isOk())
            .andExpect(view().name("aAddNewVeh"));
    }

    @Test
    void testAddNewVehicle2() throws Exception {
        when(cAdmin.getByBranchId(any())).thenReturn(B);
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AddNewVehicle/Added")
        .flashAttr("vehicledb", new Vehicledb("vehicle1", "toyota", "blue", 5, 20000, B, 10, "approved")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(view().name("aAddNewVeh"));
    }

    @Test
    void testAddNewVehicle2WithError() throws Exception {
        when(cAdmin.getByVehicleId(any())).thenReturn(new Vehicledb("vehicle1", "toyota", "blue", 5, 20000, B, 10, "approved"));
        this.mockMvc.perform(get("/OnlineVehicleBookingSystem/AddNewVehicle/Added")
        .flashAttr("vehicledb", new Vehicledb("vehicle1", "toyota", "blue", 5, 20000, B, 10, "approved")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("msg"))
                .andExpect(view().name("aAddNewVeh"));
    }

}
