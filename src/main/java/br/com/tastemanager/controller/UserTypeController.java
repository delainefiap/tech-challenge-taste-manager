package br.com.tastemanager.controller;

import br.com.tastemanager.controller.openapi.UserTypeControllerDocs;
import br.com.tastemanager.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.dto.response.UserTypeResponseDTO;
import br.com.tastemanager.service.UserTypeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user-type")
@Tag(name = "2. User Type Controller", description = "Operações de tipo de usuário")
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