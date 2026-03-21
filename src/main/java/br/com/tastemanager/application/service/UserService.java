package br.com.tastemanager.application.service;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        // Hash da senha
        user.setPassword(passwordService.hashPassword(user.getPassword()));
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
        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException ex) {
            throw new IllegalArgumentException("User cannot be deleted because it is associated with other records.");
        }
        return "User deleted successfully";
    }

    public void updatePassword(Long id, ChangePasswordRequestDTO changePasswordRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        // Validação segura da senha antiga
        if (!passwordService.isPasswordValid(id, changePasswordRequestDTO.getOldPassword())) {
             throw new IllegalArgumentException("Invalid old password");
        }
        // Hash da nova senha
        user.setPassword(passwordService.hashPassword(changePasswordRequestDTO.getNewPassword()));
        user.setLastUpdate(new Date());
        userRepository.save(user);
    }

    public boolean validateLogin(String login, String password) {
        Optional<User> user = userRepository.findByLogin(login);
        return user.isPresent() && passwordService.isPasswordValid(user.get().getId(), password);
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
