package br.com.tastemanager.application.service;

import br.com.tastemanager.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private static final Logger logger = LoggerFactory.getLogger(PasswordService.class);
    private final UserRepository userRepository;

    public PasswordService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isPasswordValid(Long id, String password) {
        return userRepository.findById(id)
                .map(user -> user.getPassword().equals(password))
                .orElse(false);
    }

    public void validateOldPassword(boolean isValid) {
        if (!isValid) {
            throw new IllegalArgumentException("Old password is incorrect");
        }
    }
}