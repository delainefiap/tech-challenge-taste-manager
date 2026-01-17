package br.com.tastemanager.validator;

import br.com.tastemanager.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserValidator {

    private final UserRepository userRepository;


    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void validateLoginAvailability(String login) {
        if (userRepository.findIdByLogin(login).isPresent()) {
            throw new IllegalArgumentException("This login is unavailable. Please choose a different one.");
        }
    }

    public void validateUserExistsById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("User with the given ID does not exist.");
        }
    }

    public void validateEmailAvailability(String email) {
        if (userRepository.findAll().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(email))) {
            throw new IllegalArgumentException("This email is unavailable. Please choose a different one.");
        }
    }

    public void validateUserName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank or empty.");
        }
    }

    public void validateUserEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("E-mail cannot be blank or empty and must be valid.");
        }
    }
}
