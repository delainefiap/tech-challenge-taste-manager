package br.com.tastemanager.service;

import br.com.tastemanager.application.service.PasswordService;
import br.com.tastemanager.application.service.UserService;
import br.com.tastemanager.domain.entity.User;
import br.com.tastemanager.domain.entity.UserType;
import br.com.tastemanager.domain.repository.UserRepository;
import br.com.tastemanager.domain.repository.UserTypeRepository;
import br.com.tastemanager.shared.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.shared.dto.request.UserRequestDTO;
import br.com.tastemanager.shared.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.UserResponseDTO;
import br.com.tastemanager.shared.exception.UserNotFoundException;
import br.com.tastemanager.shared.mapper.UserMapper;
import br.com.tastemanager.shared.validator.UserTypeValidator;
import br.com.tastemanager.shared.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordService passwordService;
    @Mock
    private UserValidator userValidator;
    @Mock
    private UserTypeValidator userTypeValidator;
    @Mock
    private UserTypeRepository userTypeRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_WithValidRequest_ReturnsMappedResponse() {
        UserType userTypeRef = new UserType();
        userTypeRef.setId(1L);

        UserRequestDTO request = new UserRequestDTO();
        request.setName("John");
        request.setEmail("john@example.com");
        request.setLogin("johndoe");
        request.setPassword("123");
        request.setUserTypeId(userTypeRef);

        User entity = new User();
        entity.setLogin("johndoe");

        UserType dbUserType = new UserType();
        dbUserType.setId(1L);
        dbUserType.setName("ADMIN");

        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setLogin("johndoe");

        when(userMapper.UserRequestDtoToEntity(request)).thenReturn(entity);
        when(userTypeRepository.findById(1L)).thenReturn(Optional.of(dbUserType));
        when(userRepository.save(entity)).thenReturn(entity);
        when(userMapper.userToUserResponseDto(entity)).thenReturn(responseDTO);

        UserResponseDTO response = userService.createUser(request);

        assertNotNull(response);
        assertEquals("johndoe", response.getLogin());
        assertNotNull(entity.getCreatedAt());
        assertNotNull(entity.getLastUpdate());
        assertEquals(dbUserType, entity.getUserTypeId());
        verify(userValidator).validateLoginAvailability("johndoe");
        verify(userValidator).validateEmailAvailability("john@example.com");
        verify(userTypeValidator).validateUserTypeId(1L);
        verify(userRepository).save(entity);
    }

    @Test
    void updateUser_WhenEmailChanges_ValidatesAndPersists() {
        Long userId = 1L;
        User existing = new User();
        existing.setEmail("old@example.com");
        existing.setLogin("john");

        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setName("John Updated");
        request.setEmail("new@example.com");
        request.setAddress("Rua A");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existing));

        String response = userService.updateUser(userId, request);

        assertEquals("User updated successfully.", response);
        assertEquals("John Updated", existing.getName());
        assertEquals("new@example.com", existing.getEmail());
        assertEquals("Rua A", existing.getAddress());
        assertNotNull(existing.getLastUpdate());
        verify(userValidator).validateEmailAvailability("new@example.com");
        verify(userRepository).save(existing);
    }

    @Test
    void updateUser_WhenUserTypeChanges_ShouldValidateAndSetType() {
        User existing = new User();
        existing.setEmail("old@example.com");

        var request = new br.com.tastemanager.shared.dto.request.UserUpdateRequestDTO();
        var userType = new br.com.tastemanager.domain.entity.UserType();
        userType.setId(2L);
        request.setUserTypeId(userType);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(userTypeRepository.findById(2L)).thenReturn(Optional.of(userType));

        String response = userService.updateUser(1L, request);

        assertEquals("User updated successfully.", response);
        assertEquals(userType, existing.getUserTypeId());
        verify(userTypeValidator).validateUserTypeId(2L);
        verify(userRepository).save(existing);
    }

    @Test
    void deleteUser_WhenNotFound_ThrowsUserNotFoundException() {
        when(userRepository.existsById(99L)).thenReturn(false);

        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(99L));
    }

    @Test
    void deleteUser_WhenDataIntegrityViolation_ThrowsIllegalArgumentException() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doThrow(new DataIntegrityViolationException("fk")).when(userRepository).deleteById(1L);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(1L));

        assertEquals("User cannot be deleted because it is associated with other records.", ex.getMessage());
    }

    @Test
    void updatePassword_WithWrongOldPassword_ThrowsException() {
        User user = new User();
        user.setPassword("old-pass");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        ChangePasswordRequestDTO dto = new ChangePasswordRequestDTO();
        dto.setOldPassword("invalid");
        dto.setNewPassword("new-pass");

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> userService.updatePassword(1L, dto));

        assertEquals("Invalid old password", ex.getMessage());
    }

    @Test
    void validateLogin_ReturnsTrueOnlyForMatchingUserAndPassword() {
        User user = new User();
        user.setPassword("123");

        when(userRepository.findByLogin("john")).thenReturn(Optional.of(user));
        when(passwordService.isPasswordValid(user.getId(), "123")).thenReturn(true);
        when(passwordService.isPasswordValid(user.getId(), "wrong")).thenReturn(false);

        assertTrue(userService.validateLogin("john", "123"));
        assertFalse(userService.validateLogin("john", "wrong"));
    }

    @Test
    void findAllUsers_ReturnsMappedList() {
        User entity = new User();
        UserResponseDTO dto = new UserResponseDTO();
        dto.setName("A");

        when(userRepository.findAll(any(org.springframework.data.domain.Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(userMapper.userToUserResponseDto(entity)).thenReturn(dto);

        List<UserResponseDTO> result = userService.findAllUsers(1, 10);

        assertEquals(1, result.size());
        assertEquals("A", result.get(0).getName());
        verify(userRepository, times(1)).findAll(any(org.springframework.data.domain.Pageable.class));
    }

    @Test
    void findUsersByName_WhenNoUsersFound_ThrowsUserNotFoundException() {
        when(userRepository.findByNameIgnoreSpaces("x")).thenReturn(List.of());

        assertThrows(UserNotFoundException.class, () -> userService.findUsersByName("x"));
    }
}
