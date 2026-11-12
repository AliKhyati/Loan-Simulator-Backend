package com.loansim.api;

import com.loansim.application.SimulationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SimulationControllerTest {

    // Configuration "standalone" : on teste le contrôleur seul, sans démarrer tout le contexte Spring
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new SimulationController(new SimulationService()))
            .build();

    @Test
    void calculate_renvoieLaMensualite() throws Exception {
        String body = """
                {
                  "principal": 200000,
                  "annualRatePercent": 3.5,
                  "durationMonths": 240
                }
                """;

        mockMvc.perform(post("/api/simulations/calculate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.monthlyPayment").value(1159.92));
    }
}
