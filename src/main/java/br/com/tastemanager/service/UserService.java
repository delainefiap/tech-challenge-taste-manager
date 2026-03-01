package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.dto.response.UserTypeIdResponseDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.entity.UserType;
import br.com.tastemanager.exception.UserNotFoundException;
import br.com.tastemanager.mapper.UserMapper;
import br.com.tastemanager.repository.UserRepository;
import br.com.tastemanager.repository.UserTypeRepository;
import br.com.tastemanager.validator.UserTypeValidator;
import br.com.tastemanager.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    private final UserTypeRepository userTypeRepository;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordService passwordService, UserValidator userValidation, UserTypeValidator userTypeValidator, UserTypeRepository userTypeRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordService = passwordService;
        this.userValidation = userValidation;
        this.userTypeValidator = userTypeValidator;
        this.userTypeRepository = userTypeRepository;
    }

    public UserResponseDTO createUser(UserRequestDTO userRequest) {
        userValidation.validateLoginAvailability(userRequest.getLogin());
        userValidation.validateEmailAvailability(userRequest.getEmail());

        Long userTypeId = userRequest.getUserTypeId() != null ? userRequest.getUserTypeId().getId() : null;
        userTypeValidator.validateUserTypeId(userTypeId);

        User user = userMapper.UserRequestDtoToEntity(userRequest);
        user.setCreatedAt(new Date());
        user.setLastUpdate(user.getCreatedAt());
        
        // Fetch UserType entity to ensure it's managed
        if (userTypeId != null) {
            UserType userType = userTypeRepository.findById(userTypeId)
                    .orElseThrow(() -> new IllegalArgumentException("UserType not found"));
            user.setUserTypeId(userType);
        }

        user = userRepository.save(user);
        logger.info("User created: {}", user.getLogin());
        
        return userMapper.userToUserResponseDto(user);
    }

    public String updateUser(Long id, UserUpdateRequestDTO userRequest) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (userRequest.getName() != null) {
            existingUser.setName(userRequest.getName());
        }
        
        if (userRequest.getEmail() != null && !userRequest.getEmail().equalsIgnoreCase(existingUser.getEmail())) {
            userValidation.validateEmailAvailability(userRequest.getEmail());
            existingUser.setEmail(userRequest.getEmail());
        }
        
        if (userRequest.getAddress() != null) {
            existingUser.setAddress(userRequest.getAddress());
        }
        
        if (userRequest.getUserTypeId() != null) {
            Long typeId = userRequest.getUserTypeId().getId();
            userTypeValidator.validateUserTypeId(typeId);
            UserType userType = userTypeRepository.findById(typeId)
                    .orElseThrow(() -> new IllegalArgumentException("UserType not found"));
            existingUser.setUserTypeId(userType);
        }

        existingUser.setLastUpdate(new Date());
        userRepository.save(existingUser);
        logger.info("User updated: {}", existingUser.getLogin());
        
        return "User updated successfully.";
    }

    public String deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);
        return "User deleted successfully";
    }

    public void updatePassword(Long id, ChangePasswordRequestDTO changePasswordRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        
        if (!user.getPassword().equals(changePasswordRequestDTO.getOldPassword())) {
             throw new IllegalArgumentException("Invalid old password");
        }

        user.setPassword(changePasswordRequestDTO.getNewPassword());
        user.setLastUpdate(new Date());
        userRepository.save(user);
    }

    public boolean validateLogin(String login, String password) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.isPresent() && user.get().getPassword().equals(password);
    }

    public List<UserResponseDTO> findAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return userRepository.findAll(pageable).getContent().stream()
                .map(userMapper::userToUserResponseDto)
                .toList();
    }

    public List<UserResponseDTO> findUsersByName(String name) {
        List<User> users = userRepository.findByNameIgnoreSpaces(name);
        if (users.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return users.stream().map(userMapper::userToUserResponseDto).toList();
    }
}
