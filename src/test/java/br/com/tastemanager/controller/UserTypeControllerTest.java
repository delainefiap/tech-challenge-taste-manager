package br.com.tastemanager.controller;

import br.com.tastemanager.application.service.UserTypeService;
import br.com.tastemanager.infrastructure.controller.UserTypeController;
import br.com.tastemanager.shared.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.shared.dto.response.UserTypeResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserTypeControllerTest {

    @Mock
    private UserTypeService userTypeService;

    @InjectMocks
    private UserTypeController userTypeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserType_ShouldReturnCreated() {
        UserTypeRequestDTO request = new UserTypeRequestDTO("admin", "desc");
        UserTypeResponseDTO responseDTO = new UserTypeResponseDTO();

        when(userTypeService.createUserType(request)).thenReturn(responseDTO);

        ResponseEntity<UserTypeResponseDTO> response = userTypeController.createUserType(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        verify(userTypeService).createUserType(request);
    }

    @Test
    void findAllUserTypes_ShouldReturnOk() {
        List<UserTypeResponseDTO> list = List.of(new UserTypeResponseDTO());
        when(userTypeService.findAllUserTypes(1, 10)).thenReturn(list);

        ResponseEntity<List<UserTypeResponseDTO>> response = userTypeController.findAllUserTypes(1, 10);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(list, response.getBody());
    }
}

