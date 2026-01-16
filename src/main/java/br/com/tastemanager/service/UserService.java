package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.mapper.UserMapper;
import br.com.tastemanager.repository.UserRepository;
import br.com.tastemanager.validator.UserTypeValidator;
import br.com.tastemanager.validator.UserValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordService passwordService;
    private final UserValidator userValidation;

    private final UserTypeValidator userTypeValidator;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordService passwordService, UserValidator userValidation, UserTypeValidator userTypeValidator) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordService = passwordService;
        this.userValidation = userValidation;
        this.userTypeValidator = userTypeValidator;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequest) {
        userValidation.validateLoginAvailability(userRequest.getLogin());
        userValidation.validateEmailAvailability(userRequest.getEmail());
        User user = userMapper.UserRequestDtoToEntity(userRequest);
        userRepository.save(user);
        logger.info("User created: {}", user.getLogin());
        return userMapper.userToUserResponseDto(user);

    }

    public String updateUser(Long id, UserUpdateRequestDTO userRequest) {
        userValidation.validateUserExistsById(id);
        if (userRequest.getName() != null && (userRequest.getName().isEmpty() || userRequest.getName().isBlank())) {
            throw new IllegalArgumentException("Name cannot be blank or empty.");
        }
        if (userRequest.getEmail() != null && (userRequest.getEmail().isEmpty() || userRequest.getEmail().isBlank() || !userRequest.getEmail().contains("@"))) {
            throw new IllegalArgumentException("E-mail cannot be blank or empty.");
        }
        User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (userRequest.getEmail() != null && !userRequest.getEmail().equalsIgnoreCase(existingUser.getEmail())) {
            userValidation.validateEmailAvailability(userRequest.getEmail());
        }
        if (userRequest.getName() != null) {
            existingUser.setName(userRequest.getName());
        }
        if (userRequest.getEmail() != null) {
            existingUser.setEmail(userRequest.getEmail());
        }
        if (userRequest.getAddress() != null) {
            existingUser.setAddress(userRequest.getAddress());
        }
        if (userRequest.getUserTypeId() != null) {
            userTypeValidator.validateUserTypeId(userRequest.getUserTypeId().getId());
            existingUser.setUserTypeId(userRequest.getUserTypeId());
        }
        existingUser.setLastUpdate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
        userRepository.save(existingUser);
        logger.info("User updated: {}", existingUser.getLogin());
        return "User updated successfully.";
    }

    public String deleteUser(Long id) {
        userValidation.validateUserExistsById(id);
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    public void updatePassword(Long id, ChangePasswordRequestDTO changePasswordRequestDTO) {
        userValidation.validateUserExistsById(id);

        if (passwordService.isPasswordValid(id, changePasswordRequestDTO.getOldPassword())) {
            User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User not found"));
            user.setPassword(changePasswordRequestDTO.getNewPassword());
            user.setLastUpdate(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Old password is incorrect");
        }
    }

    public boolean validateLogin(String login, String password) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.isPresent() && user.get().getPassword().equals(password);
    }


    public List<User> findAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return userRepository.findAll(pageable).getContent();
    }

    public List<UserResponseDTO> findUsersByName(String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        return users.stream().map(userMapper::userToUserResponseDto).toList();
    }


}
