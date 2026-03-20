package br.com.tastemanager.validator;

import br.com.tastemanager.domain.entity.UserType;
import br.com.tastemanager.domain.repository.UserRepository;
import br.com.tastemanager.domain.repository.UserTypeRepository;
import br.com.tastemanager.shared.exception.UserTypeNotFoundException;
import br.com.tastemanager.shared.validator.UserTypeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserTypeValidatorTest {

    private UserTypeRepository userTypeRepository;
    private UserRepository userRepository;
    private UserTypeValidator userTypeValidator;

    @BeforeEach
    void setUp() {
        userTypeRepository = Mockito.mock(UserTypeRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        userTypeValidator = new UserTypeValidator(userTypeRepository, userRepository);
    }

    @Test
    void validateUserTypeName_WhenAvailable_ShouldNotThrow() {
        when(userTypeRepository.findByName("ADMIN")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> userTypeValidator.validateUserTypeName("ADMIN"));
    }

    @Test
    void validateUserTypeId_WhenMissing_ShouldThrowTypedException() {
        UserType userType = new UserType();
        userType.setId(1L);
        userType.setName("ADMIN");

        when(userTypeRepository.findById(9L)).thenReturn(Optional.empty());
        when(userTypeRepository.findAll()).thenReturn(List.of(userType));

        assertThrows(UserTypeNotFoundException.class, () -> userTypeValidator.validateUserTypeId(9L));
    }

    @Test
    void validateUserTypeId_WhenNull_ShouldThrow() {
        when(userTypeRepository.findById(null)).thenReturn(Optional.empty());
        when(userTypeRepository.findAll()).thenReturn(List.of());

        assertThrows(UserTypeNotFoundException.class, () -> userTypeValidator.validateUserTypeId(null));
    }

    @Test
    void validateUserTypeExistsById_WhenExists_ShouldNotThrow() {
        when(userTypeRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> userTypeValidator.validateUserTypeExistsById(1L));
    }

    @Test
    void validateUserTypeIsInUse_WhenCountGreaterThanZero_ShouldThrow() {
        when(userRepository.countByUserTypeId(2L)).thenReturn(3L);

        assertThrows(IllegalArgumentException.class, () -> userTypeValidator.validateUserTypeIsInUse(2L));
    }

    @Test
    void validateUserTypeIsInUse_WhenZero_ShouldNotThrow() {
        when(userRepository.countByUserTypeId(2L)).thenReturn(0L);

        assertDoesNotThrow(() -> userTypeValidator.validateUserTypeIsInUse(2L));
    }
}
