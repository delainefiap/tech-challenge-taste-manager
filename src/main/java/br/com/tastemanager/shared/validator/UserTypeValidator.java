package br.com.tastemanager.shared.validator;

import br.com.tastemanager.domain.entity.UserType;
import br.com.tastemanager.domain.repository.UserRepository;
import br.com.tastemanager.domain.repository.UserTypeRepository;
import br.com.tastemanager.shared.exception.UserTypeNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserTypeValidator {

    private final UserTypeRepository userTypeRepository;

    private final UserRepository userRepository;

    public UserTypeValidator(UserTypeRepository userTypeRepository, UserRepository userRepository) {
        this.userTypeRepository = userTypeRepository;
        this.userRepository = userRepository;
    }

    public void validateUserTypeName(String userTypeName) {
        if (userTypeRepository.findByName(userTypeName).isPresent()) {
            throw new IllegalArgumentException("UserType with this name already exists. Please choose a different one.");
        }
    }

    public void validateUserTypeId(Long userTypeId) {
        if (userTypeId == null || userTypeRepository.findById(userTypeId).isEmpty()) {
            List<UserType> allTypes = userTypeRepository.findAll();
            StringBuilder options = new StringBuilder();
            allTypes.forEach(type -> options.append(type.getId()).append(" - ").append(type.getName().toUpperCase()).append(", "));
            if (options.length() > 2) {
                options.setLength(options.length() - 2);
            }
            throw new UserTypeNotFoundException(
                "UserTypeId not found. Choose one of this options: " + options,
                allTypes.stream().map(type -> type.getId() + " - " + type.getName().toUpperCase()).toList()
            );
        }
    }

    public void validateUserTypeIsInUse(Long id) {
        Long count = userRepository.countByUserTypeId(id);
        if (count != null && count > 0) {
            throw new IllegalArgumentException("Cannot delete UserType with associated users");
        }
    }

    public void validateUserTypeExistsById(Long id) {
        if (!userTypeRepository.existsById(id)) {
            throw new IllegalArgumentException("UserType does not exist");
        }
    }

}
