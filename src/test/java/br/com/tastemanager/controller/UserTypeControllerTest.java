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

    @Test
    void updateUserType_ShouldReturnOk() {
        UserTypeRequestDTO request = new UserTypeRequestDTO("manager", "desc");
        UserTypeResponseDTO responseDTO = new UserTypeResponseDTO();

        when(userTypeService.updateUserType(2L, request)).thenReturn(responseDTO);

        ResponseEntity<UserTypeResponseDTO> response = userTypeController.updateUserType(2L, request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        verify(userTypeService).updateUserType(2L, request);
    }

    @Test
    void deleteUserType_ShouldReturnOk() {
        when(userTypeService.deleteUserType(3L)).thenReturn("ok");

        ResponseEntity<String> response = userTypeController.deleteUserType(3L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("ok", response.getBody());
        verify(userTypeService).deleteUserType(3L);
    }

    @Test
    void findUserTypeById_ShouldReturnOk() {
        UserTypeResponseDTO responseDTO = new UserTypeResponseDTO();
        when(userTypeService.findUserTypeById(4L)).thenReturn(responseDTO);

        ResponseEntity<UserTypeResponseDTO> response = userTypeController.findUserTypeById(4L);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(responseDTO, response.getBody());
        verify(userTypeService).findUserTypeById(4L);
    }
}
