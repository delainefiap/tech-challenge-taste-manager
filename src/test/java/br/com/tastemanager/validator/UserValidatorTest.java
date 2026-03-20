package br.com.tastemanager.validator;

import br.com.tastemanager.domain.entity.User;
import br.com.tastemanager.domain.repository.UserRepository;
import br.com.tastemanager.shared.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserValidatorTest {

    private UserRepository userRepository;
    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        userValidator = new UserValidator(userRepository);
    }

    @Test
    void validateLoginAvailability_WhenAvailable_ShouldNotThrow() {
        when(userRepository.findIdByLogin("free")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> userValidator.validateLoginAvailability("free"));
    }

    @Test
    void validateLoginAvailability_WhenUnavailable_ShouldThrow() {
        when(userRepository.findIdByLogin("taken")).thenReturn(Optional.of(1L));

        assertThrows(IllegalArgumentException.class, () -> userValidator.validateLoginAvailability("taken"));
    }

    @Test
    void validateEmailAvailability_WhenAlreadyExists_ShouldThrow() {
        User user = new User();
        user.setEmail("john@example.com");
        when(userRepository.findAll()).thenReturn(List.of(user));

        assertThrows(IllegalArgumentException.class, () -> userValidator.validateEmailAvailability("JOHN@example.com"));
    }

    @Test
    void validateUserEmail_WhenMalformed_ShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> userValidator.validateUserEmail("invalid"));
    }

    @Test
    void validateUserExistsById_WhenUserExists_ShouldNotThrow() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));

        assertDoesNotThrow(() -> userValidator.validateUserExistsById(1L));
    }

    @Test
    void validateUserExistsById_WhenUserMissing_ShouldThrow() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userValidator.validateUserExistsById(99L));
    }

    @Test
    void validateEmailAvailability_WhenAvailable_ShouldNotThrow() {
        User user = new User();
        user.setEmail("john@example.com");
        when(userRepository.findAll()).thenReturn(List.of(user));

        assertDoesNotThrow(() -> userValidator.validateEmailAvailability("jane@example.com"));
    }

    @Test
    void validateUserName_WhenBlank_ShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> userValidator.validateUserName("  "));
    }

    @Test
    void validateUserName_WhenValid_ShouldNotThrow() {
        assertDoesNotThrow(() -> userValidator.validateUserName("John"));
    }

    @Test
    void validateUserEmail_WhenValid_ShouldNotThrow() {
        assertDoesNotThrow(() -> userValidator.validateUserEmail("john@example.com"));
    }
}