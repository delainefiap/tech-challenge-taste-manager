package br.com.tastemanager.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiSmokeIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldLoadUsersEndpointWithDatabase() throws Exception {
        mockMvc.perform(get("/api/v1/user/find-all")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldLoadUserTypesEndpointWithDatabase() throws Exception {
        mockMvc.perform(get("/api/v1/user-type/find-all")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldLoadRestaurantsEndpointWithDatabase() throws Exception {
        mockMvc.perform(get("/api/v1/restaurant/find-all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void shouldLoadMenuEndpointWithDatabase() throws Exception {
        mockMvc.perform(get("/api/v1/menu/find-all")
                        .param("page", "1")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
