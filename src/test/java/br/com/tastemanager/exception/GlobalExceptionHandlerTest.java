package br.com.tastemanager.exception;

import br.com.tastemanager.mapper.UserMapper;
import br.com.tastemanager.repository.UserRepository;
import br.com.tastemanager.service.PasswordService;
import br.com.tastemanager.service.UserService;
import br.com.tastemanager.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class GlobalExceptionHandlerTest {
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private UserValidator userValidator;
//
//    @MockBean
//    private PasswordService passwordService;
//
//    @MockBean
//    private UserMapper userMapper;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @BeforeEach
//    void setUp() {
//
//    }
//
//    @Test
//    void handleValidationExceptions_ShouldReturnBadRequest() throws Exception {
//        String invalidRequestBody = "{\n" +
//                "  \"login\": \"\"\n" +
//                "}";
//
//        mockMvc.perform(patch("/user/update/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(invalidRequestBody))
//                .andExpect(status().isBadRequest())
//                .andExpect(status().isBadRequest())
//                .andExpect(result -> assertEquals(
//                        "It is not possible to change this field or it doesn't exist : login",
//                        result.getResponse().getContentAsString()
//                ));
//    }
//
//    @Test
//    void handleIllegalArgumentException_ShouldReturnBadRequest() {
//        GlobalExceptionHandler handler = new GlobalExceptionHandler();
//        IllegalArgumentException exception = new IllegalArgumentException("Invalid argument");
//
//        ResponseEntity<String> response = handler.handleIllegalArgumentException(exception);
//
//        assertEquals(400, response.getStatusCode().value());
//        assertEquals("Invalid argument", response.getBody());
//    }

}