package com.powerbi.token.controller;

import com.powerbi.token.service.PowerBIService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(PowerBIController.class)
class PowerBIControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PowerBIService powerBIService;

    @Test
    void healthEndpointReturnsOk() throws Exception {
        mockMvc.perform(get("/api/powerbi/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Power BI Token Service is running"));
    }
}
