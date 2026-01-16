package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.helper.TestDataHelper;
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

//    @Mock
//    private UserRepository userRepository;
//
//    @Mock
//    private UserMapper userMapper;
//
//    @Mock
//    private PasswordService passwordService;
//
//    @Mock
//    private UserValidator userValidator;
//
//    @InjectMocks
//    private UserService userService;
//
//
//    private ChangePasswordRequestDTO buildChangePasswordRequest(String oldPass, String newPass) {
//        ChangePasswordRequestDTO dto = new ChangePasswordRequestDTO();
//        dto.setOldPassword(oldPass);
//        dto.setNewPassword(newPass);
//        return dto;
//    }
//
//
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void createUser_ValidRequest_Success() {
//        UserRequestDTO request = TestDataHelper.buildValidUserRequest();
//        User user = new User();
//
//        UserResponseDTO responseDTO = new UserResponseDTO();
//        responseDTO.setName("John");
//        responseDTO.setEmail("john@example.com");
//        responseDTO.setLogin("johndoe");
//
//        doNothing().when(userValidator).validateLoginAvailability(request.getLogin());
//        when(userMapper.UserRequestDtoToEntity(request)).thenReturn(user);
//        when(userMapper.userToUserResponseDto(user)).thenReturn(responseDTO);
//
//        UserResponseDTO response = userService.createUser(request);
//
//        assertNotNull(response);
//        assertEquals("John", response.getName());
//        verify(userRepository, times(1)).save(user);
//    }
//
//
//    @Test
//    void updateUser_ValidRequest_Success() {
//        Long userId = 1L;
//        UserUpdateRequestDTO request = TestDataHelper.buildValidUserUpdateRequest();
//        User user = new User();
//
//        doNothing().when(userValidator).validateUserExistsById(userId);
//        when(userMapper.userUpdateRequestDtoToEntity(request)).thenReturn(user);
//
//        String result = userService.updateUser(userId, request);
//
//        assertEquals("User updated successfully", result);
//        verify(userRepository, times(1)).updateUser(userId, user);
//    }
//
//    @Test
//    void updateUser_InvalidName_ThrowsException() {
//        Long userId = 1L;
//        UserUpdateRequestDTO request = TestDataHelper.buildValidUserUpdateRequest();
//        request.setName("   ");
//
//        doNothing().when(userValidator).validateUserExistsById(userId);
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            userService.updateUser(userId, request);
//        });
//
//        assertEquals("Name cannot be blank or empty.", exception.getMessage());
//    }
//
//    @Test
//    void updateUser_InvalidEmail_ThrowsException() {
//        Long userId = 1L;
//        UserUpdateRequestDTO request = TestDataHelper.buildValidUserUpdateRequest();
//        request.setEmail("   ");
//
//        doNothing().when(userValidator).validateUserExistsById(userId);
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
//            userService.updateUser(userId, request);
//        });
//
//        assertEquals("E-mail cannot be blank or empty.", exception.getMessage());
//    }
//
//    @Test
//    void deleteUser_ValidId_Success() {
//        Long userId = 1L;
//
//        doNothing().when(userValidator).validateUserExistsById(userId);
//
//        String result = userService.deleteUser(userId);
//
//        assertEquals("User deleted successfully", result);
//        verify(userRepository).deleteUser(userId);
//    }
//
//    @Test
//    void updatePassword_ValidOldPassword_Success() {
//        Long userId = 1L;
//        ChangePasswordRequestDTO request = TestDataHelper.buildChangePasswordRequest("oldPass", "newPass");
//
//        doNothing().when(userValidator).validateUserExistsById(userId);
//        when(passwordService.isPasswordValid(userId, request.getOldPassword())).thenReturn(true);
//
//        userService.updatePassword(userId, request);
//
//        verify(userRepository).updatePassword(userId, "newPass");
//    }
//
//    @Test
//    void updatePassword_InvalidOldPassword_ThrowsException() {
//        Long userId = 1L;
//        ChangePasswordRequestDTO request = TestDataHelper.buildChangePasswordRequest("wrongPass", "newPass");
//
//        doNothing().when(userValidator).validateUserExistsById(userId);
//        when(passwordService.isPasswordValid(userId, request.getOldPassword())).thenReturn(false);
//
//        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
//                userService.updatePassword(userId, request));
//
//        assertEquals("Old password is incorrect", exception.getMessage());
//    }
//
//    @Test
//    void validateLogin_ValidCredentials_ReturnsTrue() {
//        String login = "johndoe";
//        String password = "123";
//        User user = new User();
//        user.setLogin(login);
//        user.setPassword(password);
//
//        when(userRepository.findAll(1, 0)).thenReturn(List.of(user));
//
//        boolean isValid = userService.validateLogin(login, password);
//
//        assertTrue(isValid);
//    }
//
//    @Test
//    void validateLogin_InvalidCredentials_ReturnsFalse() {
//        when(userRepository.findAll(1, 0)).thenReturn(Collections.emptyList());
//
//        boolean isValid = userService.validateLogin("wrong", "wrong");
//
//        assertFalse(isValid);
//    }
//
//    @Test
//    void findAllUsers_ReturnsPagedUsers() {
//        int page = 1;
//        int size = 5;
//        List<User> users = List.of(new User(), new User());
//
//        when(userRepository.findAll(size, 0)).thenReturn(users);
//
//        List<User> result = userService.findAllUsers(page, size);
//
//        assertEquals(2, result.size());
//        verify(userRepository).findAll(size, 0);
//    }
//

}
