package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.mapper.UserMapper;
import br.com.tastemanager.repository.UserRepository;
import br.com.tastemanager.validator.UserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private br.com.tastemanager.validator.UserTypeValidator userTypeValidator;
    @Mock
    private br.com.tastemanager.repository.UserTypeRepository userTypeRepository;
    @InjectMocks
    private UserService userService;


    private ChangePasswordRequestDTO buildChangePasswordRequest(String oldPass, String newPass) {
        ChangePasswordRequestDTO dto = new ChangePasswordRequestDTO();
        dto.setOldPassword(oldPass);
        dto.setNewPassword(newPass);
        return dto;
    }



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUser_ValidRequest_Success() {
        UserRequestDTO request = new UserRequestDTO();
        request.setLogin("johndoe");
        request.setEmail("john@example.com");
        request.setName("John");
        User user = new User();
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setName("John");
        responseDTO.setEmail("john@example.com");
        responseDTO.setLogin("johndoe");
        doNothing().when(userValidator).validateLoginAvailability(request.getLogin());
        doNothing().when(userValidator).validateEmailAvailability(request.getEmail());
        doNothing().when(userTypeValidator).validateUserTypeId(any());
        when(userMapper.UserRequestDtoToEntity(request)).thenReturn(user);
        when(userMapper.userToUserResponseDto(user)).thenReturn(responseDTO);
        when(userRepository.save(user)).thenReturn(user);
        UserResponseDTO response = userService.createUser(request);
        assertNotNull(response);
        assertEquals("John", response.getName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void updateUser_ValidRequest_Success() {
        Long userId = 1L;
        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setName("John Updated");
        request.setEmail("john.updated@example.com");
        User user = new User();
        user.setEmail("old@example.com");
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        doNothing().when(userValidator).validateUserExistsById(userId);
        doNothing().when(userValidator).validateUserName(request.getName());
        doNothing().when(userValidator).validateUserEmail(request.getEmail());
        doNothing().when(userValidator).validateEmailAvailability(request.getEmail());
        when(userRepository.save(any(User.class))).thenReturn(user);
        String result = userService.updateUser(userId, request);
        assertEquals("User updated successfully.", result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void updateUser_InvalidName_DoesNotThrow() {
        Long userId = 1L;
        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setName(null);
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(new User()));
        doNothing().when(userValidator).validateUserExistsById(userId);
        assertDoesNotThrow(() -> userService.updateUser(userId, request));
    }

    @Test
    void updateUser_InvalidEmail_DoesNotThrow() {
        Long userId = 1L;
        UserUpdateRequestDTO request = new UserUpdateRequestDTO();
        request.setEmail(null);
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(new User()));
        doNothing().when(userValidator).validateUserExistsById(userId);
        assertDoesNotThrow(() -> userService.updateUser(userId, request));
    }

    @Test
    void deleteUser_ValidId_Success() {
        Long userId = 1L;
        doNothing().when(userValidator).validateUserExistsById(userId);
        doNothing().when(userRepository).deleteById(userId);
        String result = userService.deleteUser(userId);
        assertEquals("User deleted successfully", result);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void updatePassword_ValidOldPassword_Success() {
        Long userId = 1L;
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO();
        request.setOldPassword("oldPass");
        request.setNewPassword("newPass");
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));
        doNothing().when(userValidator).validateUserExistsById(userId);
        when(passwordService.isPasswordValid(userId, request.getOldPassword())).thenReturn(true);
        when(userRepository.save(any(User.class))).thenReturn(user);
        userService.updatePassword(userId, request);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void updatePassword_InvalidOldPassword_ThrowsException() {
        Long userId = 1L;
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO();
        request.setOldPassword("wrongPass");
        request.setNewPassword("newPass");
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(new User()));
        doNothing().when(userValidator).validateUserExistsById(userId);
        when(passwordService.isPasswordValid(userId, request.getOldPassword())).thenReturn(false);
        doThrow(new IllegalArgumentException("Old password is incorrect")).when(passwordService).validateOldPassword(false);
        assertThrows(IllegalArgumentException.class, () -> userService.updatePassword(userId, request));
    }

    @Test
    void validateLogin_ValidCredentials_ReturnsTrue() {
        String login = "johndoe";
        String password = "123";
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        when(userRepository.findByLogin(login)).thenReturn(java.util.Optional.of(user));
        boolean isValid = userService.validateLogin(login, password);
        assertTrue(isValid);
    }

    @Test
    void validateLogin_InvalidCredentials_ReturnsFalse() {
        String login = "wrong";
        String password = "wrong";
        when(userRepository.findByLogin(login)).thenReturn(java.util.Optional.empty());
        boolean isValid = userService.validateLogin(login, password);
        assertFalse(isValid);
    }

    @Test
    void findAllUsers_ReturnsPagedUsers() {
        int page = 1;
        int size = 5;
        List<User> users = List.of(new User(), new User());
        org.springframework.data.domain.Page<User> pageResult = new org.springframework.data.domain.PageImpl<>(users);
        when(userRepository.findAll(any(org.springframework.data.domain.Pageable.class))).thenReturn(pageResult);
        when(userMapper.userToUserResponseDto(any(User.class))).thenReturn(new UserResponseDTO());
        List<UserResponseDTO> result = userService.findAllUsers(page, size);
        assertEquals(2, result.size());
        verify(userRepository).findAll(any(org.springframework.data.domain.Pageable.class));
    }


}
