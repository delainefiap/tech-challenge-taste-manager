package br.com.tastemanager.infrastructure.controller;

import br.com.tastemanager.application.service.UserTypeService;
import br.com.tastemanager.infrastructure.controller.openapi.UserTypeControllerDocs;
import br.com.tastemanager.shared.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.shared.dto.response.UserTypeResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/user-type")
public class UserTypeController implements UserTypeControllerDocs {
    private final UserTypeService userTypeService;

    public UserTypeController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    public ResponseEntity<UserTypeResponseDTO> createUserType(@RequestBody UserTypeRequestDTO userTypeRequestDTO) {
        UserTypeResponseDTO response = userTypeService.createUserType(userTypeRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    public ResponseEntity<?> findAllUserTypes(@RequestParam int page, @RequestParam int size) {
        var response = userTypeService.findAllUserTypes(page, size);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<UserTypeResponseDTO> updateUserType(@PathVariable Long id, @RequestBody UserTypeRequestDTO userTypeRequestDTO) {
        UserTypeResponseDTO response = userTypeService.updateUserType(id, userTypeRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<String> deleteUserType(@PathVariable Long id) {
        var response = userTypeService.deleteUserType(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<UserTypeResponseDTO> findUserTypeById(@PathVariable Long id) {
        UserTypeResponseDTO response = userTypeService.findUserTypeById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}