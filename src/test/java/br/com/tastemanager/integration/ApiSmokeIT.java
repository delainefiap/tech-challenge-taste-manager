package br.com.tastemanager.integration;

import br.com.tastemanager.shared.dto.request.MenuRequestDTO;
import br.com.tastemanager.shared.dto.request.UserRequestDTO;
import br.com.tastemanager.shared.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.shared.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.domain.entity.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ApiSmokeIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private static Long createdUserTypeId;
    private static Long createdUserId;
    private static Long createdRestaurantId;
    private static Long createdMenuId;

    @Test
    @Order(1)
    void shouldCreateUserType() throws Exception {
        UserTypeRequestDTO request = new UserTypeRequestDTO();
        request.setName("INTEGRATION_CLIENTE");
        request.setDescription("desc");
        MvcResult result = mockMvc.perform(post("/api/v1/user-type/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();
        createdUserTypeId = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    @Order(2)
    void shouldCreateUser() throws Exception {
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Integration User");
        request.setEmail("integration@user.com");
        request.setLogin("integrationuser");
        request.setPassword("123456");
        UserType userType = new UserType();
        userType.setId(createdUserTypeId);
        request.setUserTypeId(userType);
        request.setAddress("Rua Teste, 123");
        MvcResult result = mockMvc.perform(post("/api/v1/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();
        createdUserId = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    @Order(3)
    void shouldCreateRestaurant() throws Exception {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("Integration Restaurant");
        request.setAddress("Rua Restaurante, 456");
        request.setTypeKitchen("Brasileira");
        request.setOpeningHours("08:00-18:00");
        request.setOwnerId(createdUserId);
        MvcResult result = mockMvc.perform(post("/api/v1/restaurant/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn();
        createdRestaurantId = objectMapper.readTree(result.getResponse().getContentAsString()).get("id").asLong();
    }

    @Test
    @Order(4)
    void shouldCreateMenuItem() throws Exception {
        MenuRequestDTO menu = new MenuRequestDTO();
        menu.setName("Prato Teste");
        menu.setDescription("Descrição do prato");
        menu.setPrice(10.0);
        menu.setAvailableOnlyAtRestaurant(false);
        menu.setImagePath("/img/teste.jpg");
        List<MenuRequestDTO> menuList = List.of(menu);
        MvcResult result = mockMvc.perform(post("/api/v1/menu/create/" + createdRestaurantId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(menuList)))
                .andExpect(status().isCreated())
                .andReturn();
        createdMenuId = objectMapper.readTree(result.getResponse().getContentAsString()).get(0).get("id").asLong();
    }

    @Test
    @Order(5)
    void shouldListAll() throws Exception {
        mockMvc.perform(get("/api/v1/user/find-all").param("page", "1").param("size", "10"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/user-type/find-all").param("page", "1").param("size", "10"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/restaurant/find-all"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/menu/find-all").param("page", "1").param("size", "10"))
                .andExpect(status().isOk());
    }

    @Test
    @Order(6)
    void shouldUpdateUserType() throws Exception {
        UserTypeRequestDTO request = new UserTypeRequestDTO();
        request.setName("INTEGRATION_CLIENTE_EDITED");
        request.setDescription("desc editada");
        mockMvc.perform(patch("/api/v1/user-type/update/" + createdUserTypeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("INTEGRATION_CLIENTE_EDITED")));
    }

    @Test
    @Order(7)
    void shouldUpdateUser() throws Exception {
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Integration User Updated");
        request.setEmail("integration@user.com");
        request.setLogin("integrationuser");
        request.setPassword("123456");
        UserType userType = new UserType();
        userType.setId(createdUserTypeId);
        request.setUserTypeId(userType);
        request.setAddress("Rua Teste, 123");
        mockMvc.perform(patch("/api/v1/user/update/" + createdUserId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(8)
    void shouldUpdateRestaurant() throws Exception {
        RestaurantRequestDTO request = new RestaurantRequestDTO();
        request.setName("Integration Restaurant Updated");
        request.setAddress("Rua Restaurante, 456");
        request.setTypeKitchen("Brasileira");
        request.setOpeningHours("08:00-18:00");
        request.setOwnerId(createdUserId);
        mockMvc.perform(patch("/api/v1/restaurant/update/" + createdRestaurantId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Integration Restaurant Updated")));
    }

    @Test
    @Order(9)
    void shouldDeleteMenuItem() throws Exception {
        mockMvc.perform(delete("/api/v1/menu/delete-item")
                .param("restaurantId", String.valueOf(createdRestaurantId))
                .param("restaurantItemNumber", String.valueOf(1)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(10)
    void shouldDeleteRestaurant() throws Exception {
        mockMvc.perform(delete("/api/v1/restaurant/delete")
                .param("id", String.valueOf(createdRestaurantId)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(11)
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/v1/user/delete")
                .param("id", String.valueOf(createdUserId)))
                .andExpect(status().isOk());
    }

    @Test
    @Order(12)
    void shouldDeleteUserType() throws Exception {
        mockMvc.perform(delete("/api/v1/user-type/delete")
                .param("id", String.valueOf(createdUserTypeId)))
                .andExpect(status().isOk());
    }
}
