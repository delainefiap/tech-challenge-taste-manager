package br.com.tastemanager.integration;

import br.com.tastemanager.shared.dto.request.RestaurantRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Sql(scripts = "/data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class RestaurantIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static Long createdRestaurantId;

    @Test
    @Order(1)
    void shouldCreateRestaurant_whenValidDataIsProvided() throws Exception {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("The Integration Test Bistro");
        request.setAddress("123 Test Lane");
        request.setTypeKitchen("Java Cuisine");
        request.setOpeningHours("10:00-22:00");
        request.setOwnerId(1L);

        MvcResult result = mockMvc.perform(post("/api/v1/restaurant/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("The Integration Test Bistro")))
                .andReturn();

        // Extract ID for subsequent tests
        String jsonResponse = result.getResponse().getContentAsString();
        createdRestaurantId = objectMapper.readTree(jsonResponse).get("id").asLong();
    }

    @Test
    @Order(2)
    void shouldFindCreatedRestaurantById() throws Exception {
        mockMvc.perform(get("/api/v1/restaurant/find-by-id")
                        .param("id", String.valueOf(createdRestaurantId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(createdRestaurantId.intValue())))
                .andExpect(jsonPath("$.name", is("The Integration Test Bistro")));
    }

    @Test
    @Order(3)
    void shouldUpdateRestaurant() throws Exception {
        RestaurantRequestDTO updateRequest = new RestaurantRequestDTO();
        updateRequest.setName("The Updated Test Bistro");
        updateRequest.setAddress("456 Updated Ave");
        updateRequest.setTypeKitchen("Kotlin Cuisine");
        updateRequest.setOpeningHours("11:00-23:00");
        updateRequest.setOwnerId(1L);

        mockMvc.perform(patch("/api/v1/restaurant/update/" + createdRestaurantId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("The Updated Test Bistro")))
                .andExpect(jsonPath("$.address", is("456 Updated Ave")));
    }

    @Test
    @Order(4)
    void shouldReturnConflict_whenCreatingRestaurantWithDuplicateName() throws Exception {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("The Updated Test Bistro"); // Name already used in the update test
        request.setAddress("789 Conflict St");
        request.setTypeKitchen("Error Cuisine");
        request.setOpeningHours("00:00-00:00");
        request.setOwnerId(1L);

        mockMvc.perform(post("/api/v1/restaurant/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(5)
    void shouldDeleteRestaurant() throws Exception {
        mockMvc.perform(delete("/api/v1/restaurant/delete")
                        .param("id", String.valueOf(createdRestaurantId)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    void shouldReturnNotFound_afterRestaurantIsDeleted() throws Exception {
        mockMvc.perform(get("/api/v1/restaurant/find-by-id")
                        .param("id", String.valueOf(createdRestaurantId)))
                .andExpect(status().isNotFound());
    }
}
