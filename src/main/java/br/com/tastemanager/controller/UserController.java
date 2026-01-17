package br.com.tastemanager.controller;

import br.com.tastemanager.controller.openapi.UserControllerDocs;
import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController implements UserControllerDocs {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequest) {
        var response = this.userService.createUser(userRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<String> updateUser(@PathVariable Long id,
                                             @RequestBody UserUpdateRequestDTO userRequest) {
        var response = this.userService.updateUser(id, userRequest);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }


    public ResponseEntity<String> deleteUser(@RequestParam Long id) {
        var response = this.userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    public ResponseEntity<String> changePassword(@PathVariable Long id,
                                                 @Valid @RequestBody ChangePasswordRequestDTO changePasswordRequestDTO) {
        this.userService.updatePassword(id, changePasswordRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("Password changed successfully.");
    }


    public ResponseEntity<String> validateLogin(@RequestParam String login, @RequestParam String password) {
        boolean isValid = userService.validateLogin(login, password);
        return isValid ? ResponseEntity.ok("Login successful")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    public ResponseEntity<?> findAllUsers(@RequestParam int page, @RequestParam int size) {
        var users = userService.findAllUsers(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }


    public ResponseEntity<List<UserResponseDTO>> findUsersByName(@RequestParam String name) {
        var users = userService.findUsersByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
