package br.com.tastemanager.controller;

import br.com.tastemanager.infrastructure.controller.UserController;
import br.com.tastemanager.shared.exception.GlobalExceptionHandler;
import br.com.tastemanager.application.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class UserControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private GlobalExceptionHandler globalExceptionHandler;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        globalExceptionHandler = new GlobalExceptionHandler();
        objectMapper = new ObjectMapper();

        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(globalExceptionHandler)
                .build();
    }

    @Test
    void deleteUser_WithDataIntegrityViolation_ShouldReturn409() throws Exception {
        Long userId = 2L;

        when(userService.deleteUser(userId)).thenThrow(
            new DataIntegrityViolationException("Cannot delete or update a parent row: a foreign key constraint fails")
        );

        mockMvc.perform(delete("/api/v1/user/delete")
                .param("id", userId.toString()))
                .andExpect(status().isConflict())
                .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.type").value("https://datatracker.ietf.org/doc/html/rfc7807#section-3.1"))
                .andExpect(jsonPath("$.title").value("Data Integrity Violation"))
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.detail").value("Não foi possível excluir o usuário pois ele está associado a outros registros (por exemplo, restaurantes)."))
                .andExpect(jsonPath("$.instance").value("/api/v1/user/delete"));
    }

    @Test
    void deleteUser_Success_ShouldReturn200() throws Exception {
        Long userId = 1L;
        String expectedResponse = "User deleted successfully";

        when(userService.deleteUser(userId)).thenReturn(expectedResponse);

        mockMvc.perform(delete("/api/v1/user/delete")
                .param("id", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
}
