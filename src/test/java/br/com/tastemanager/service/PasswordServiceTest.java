package br.com.tastemanager.service;

import br.com.tastemanager.entity.User;
import br.com.tastemanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PasswordServiceTest {

//    @Mock
//    private UserRepository userRepository;
//
//    private PasswordService passwordService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        passwordService = new PasswordService(userRepository);
//    }
//
//    @Test
//    void testIsPasswordValid_WhenPasswordMatches() {
//        Long id = 1L;
//        String login = "testUser";
//        String password = "password123";
//        User user = new User();
//        user.setPassword(password);
//
//        when(userRepository.findById(id)).thenReturn(Optional.of(user));
//
//        boolean isValid = passwordService.isPasswordValid(id, password);
//
//        assertTrue(isValid);
//        verify(userRepository, times(1)).findById(id);
//    }
//
//    @Test
//    void testIsPasswordValid_WhenPasswordDoesNotMatch() {
//        Long id = 1L;
//        String login = "testUser";
//        String password = "wrongPassword";
//        User user = new User();
//        user.setPassword("password123");
//
//        when(userRepository.findById(id)).thenReturn(Optional.of(user));
//
//        boolean isValid = passwordService.isPasswordValid(id, password);
//
//        assertFalse(isValid);
//        verify(userRepository, times(1)).findById(id);
//    }
//
//    @Test
//    void testIsPasswordValid_WhenUserNotFound() {
//        Long id = 1L;
//        String login = "nonExistentUser";
//        String password = "password123";
//
////        when(userRepository.findUserByLogin(login)).thenReturn(Optional.empty());
//
//        boolean isValid = passwordService.isPasswordValid(id, password);
//
//        assertFalse(isValid);
//        verify(userRepository, times(1)).findById(id);
//    }
//
//    @Test
//    void testValidateOldPassword_WhenPasswordIsValid() {
//        assertDoesNotThrow(() -> passwordService.validateOldPassword(true));
//    }
//
//    @Test
//    void testValidateOldPassword_WhenPasswordIsInvalid() {
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
//                () -> passwordService.validateOldPassword(false));
//
//        assertEquals("Old password is incorrect", exception.getMessage());
//    }
}