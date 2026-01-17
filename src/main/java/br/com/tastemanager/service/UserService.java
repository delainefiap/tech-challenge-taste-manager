package br.com.tastemanager.service;

import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.dto.response.UserTypeIdResponseDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.exception.UserNotFoundException;
import br.com.tastemanager.exception.UserTypeNotFoundException;
import br.com.tastemanager.entity.UserType;
import br.com.tastemanager.mapper.UserMapper;
import br.com.tastemanager.repository.UserRepository;
import br.com.tastemanager.repository.UserTypeRepository;
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

        if (userTypeId == null || userTypeRepository.findById(userTypeId).isEmpty()) {
            var allTypes = userTypeRepository.findAll();
            StringBuilder options = new StringBuilder();
            allTypes.forEach(type -> options.append(type.getId()).append(" - ").append(type.getName()).append(", "));
            if (options.length() > 2) {
                options.setLength(options.length() - 2);
            }
            throw new UserTypeNotFoundException(
                "UserTypeId not found. Choose one of this options: " + options.toString(),
                allTypes.stream().map(type -> type.getId() + " - " + type.getName()).toList()
            );
        }
        User user = userMapper.UserRequestDtoToEntity(userRequest);
        user.setLastUpdate(Date.from(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant()));
        userRepository.save(user);
        logger.info("User created: {}", user.getLogin());
        UserResponseDTO responseDTO = userMapper.userToUserResponseDto(user);
        if (user.getUserTypeId() != null && user.getUserTypeId().getId() != null) {
            userTypeRepository.findById(user.getUserTypeId().getId())
                .ifPresent(userType -> responseDTO.setUserTypeId(new br.com.tastemanager.dto.response.UserTypeIdResponseDTO(userType.getName())));
        }
        return responseDTO;

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
        existingUser.setLastUpdate(Date.from(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant()));
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
            user.setLastUpdate(Date.from(LocalDateTime.now().atZone(ZoneId.of("America/Sao_Paulo")).toInstant()));
            userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Old password is incorrect");
        }
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
