package br.com.tastemanager.controller;

import br.com.tastemanager.infrastructure.controller.UserController;
import br.com.tastemanager.application.service.UserService;
import br.com.tastemanager.shared.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.shared.dto.request.UserRequestDTO;
import br.com.tastemanager.shared.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.UserResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        UserRequestDTO userRequest = new UserRequestDTO();
        UserResponseDTO userResponse = new UserResponseDTO();
        when(userService.createUser(userRequest)).thenReturn(userResponse);

        ResponseEntity<UserResponseDTO> response = userController.createUser(userRequest);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(userResponse, response.getBody());
        verify(userService, times(1)).createUser(userRequest);
    }

    @Test
    void testUpdateUser() {
        Long userId = 1L;
        UserUpdateRequestDTO userUpdateRequest = new UserUpdateRequestDTO();
        String expectedResponse = "User updated successfully.";

        when(userService.updateUser(userId, userUpdateRequest)).thenReturn(expectedResponse);

        ResponseEntity<String> response = userController.updateUser(userId, userUpdateRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedResponse, response.getBody());
        verify(userService, times(1)).updateUser(userId, userUpdateRequest);
    }

    @Test
    void testDeleteUser() {
        Long userId = 1L;
        String expectedResponse = "User deleted successfully";

        when(userService.deleteUser(userId)).thenReturn(expectedResponse);

        ResponseEntity<String> response = userController.deleteUser(userId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedResponse, response.getBody());
        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testDeleteUser_WithDataIntegrityViolation() {
        Long userId = 2L;

        when(userService.deleteUser(userId)).thenThrow(
            new DataIntegrityViolationException("Cannot delete or update a parent row: a foreign key constraint fails")
        );

        assertThrows(DataIntegrityViolationException.class, () -> {
            userController.deleteUser(userId);
        });

        verify(userService, times(1)).deleteUser(userId);
    }

    @Test
    void testChangePassword() {
        Long userId = 1L;
        ChangePasswordRequestDTO changePasswordRequest = new ChangePasswordRequestDTO();

        doNothing().when(userService).updatePassword(userId, changePasswordRequest);

        ResponseEntity<String> response = userController.changePassword(userId, changePasswordRequest);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Password changed successfully.", response.getBody());
        verify(userService, times(1)).updatePassword(userId, changePasswordRequest);
    }

    @Test
    void testValidateLoginSuccess() {
        String login = "test";
        String password = "password";

        when(userService.validateLogin(login, password)).thenReturn(true);

        ResponseEntity<String> response = userController.validateLogin(login, password);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Login successful", response.getBody());
        verify(userService, times(1)).validateLogin(login, password);
    }

    @Test
    void testValidateLoginFailure() {
        String login = "test";
        String password = "wrong";

        when(userService.validateLogin(login, password)).thenReturn(false);

        ResponseEntity<String> response = userController.validateLogin(login, password);

        assertEquals(401, response.getStatusCode().value());
        assertEquals("Invalid credentials", response.getBody());
        verify(userService, times(1)).validateLogin(login, password);
    }

    @Test
    void testFindAllUsers() {
        int page = 1;
        int size = 10;
        List<UserResponseDTO> users = List.of(new UserResponseDTO(), new UserResponseDTO());
        when(userService.findAllUsers(page, size)).thenReturn(users);

        ResponseEntity<?> response = userController.findAllUsers(page, size);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).findAllUsers(page, size);
    }

    @Test
    void testFindUsersByName() {
        String name = "John";
        List<UserResponseDTO> users = List.of(new UserResponseDTO());
        when(userService.findUsersByName(name)).thenReturn(users);

        ResponseEntity<List<UserResponseDTO>> response = userController.findUsersByName(name);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(users, response.getBody());
        verify(userService, times(1)).findUsersByName(name);
    }
}
