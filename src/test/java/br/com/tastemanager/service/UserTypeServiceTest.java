package br.com.tastemanager.service;

import br.com.tastemanager.application.service.UserTypeService;
import br.com.tastemanager.domain.entity.UserType;
import br.com.tastemanager.domain.repository.UserTypeRepository;
import br.com.tastemanager.shared.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.shared.dto.response.UserTypeResponseDTO;
import br.com.tastemanager.shared.mapper.UserTypeMapper;
import br.com.tastemanager.shared.validator.UserTypeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserTypeServiceTest {

    @Mock
    private UserTypeRepository userTypeRepository;
    @Mock
    private UserTypeMapper userTypeMapper;
    @Mock
    private UserTypeValidator userTypeValidator;

    @InjectMocks
    private UserTypeService userTypeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createUserType_ShouldUppercaseNameAndPersist() {
        UserTypeRequestDTO request = new UserTypeRequestDTO("admin", "desc");
        UserType entity = new UserType();
        entity.setName("admin");

        UserTypeResponseDTO responseDTO = new UserTypeResponseDTO();
        responseDTO.setName("ADMIN");

        when(userTypeMapper.toEntity(request)).thenReturn(entity);
        when(userTypeMapper.toResponseDTO(entity)).thenReturn(responseDTO);

        UserTypeResponseDTO response = userTypeService.createUserType(request);

        assertEquals("ADMIN", entity.getName());
        assertEquals("ADMIN", response.getName());
        verify(userTypeValidator).validateUserTypeName("ADMIN");
        verify(userTypeRepository).save(entity);
    }

    @Test
    void findAllUserTypes_ShouldReturnMappedPageContent() {
        UserType entity = new UserType();
        entity.setId(1L);
        entity.setName("ADMIN");

        UserTypeResponseDTO dto = new UserTypeResponseDTO();
        dto.setId(1L);

        when(userTypeRepository.findAll(PageRequest.of(1, 5, Sort.by("id"))))
                .thenReturn(new PageImpl<>(List.of(entity)));
        when(userTypeMapper.toResponseDTO(entity)).thenReturn(dto);

        List<UserTypeResponseDTO> result = userTypeService.findAllUserTypes(2, 5);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void updateUserType_WhenNotFound_ShouldThrow() {
        when(userTypeRepository.findById(99L)).thenReturn(Optional.empty());

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> userTypeService.updateUserType(99L, new UserTypeRequestDTO("x", "y")));

        assertEquals("UserType not found", ex.getMessage());
    }

    @Test
    void deleteUserType_ShouldValidateAndDelete() {
        String result = userTypeService.deleteUserType(2L);

        assertEquals("User type deleted successfully", result);
        verify(userTypeValidator).validateUserTypeExistsById(2L);
        verify(userTypeValidator).validateUserTypeIsInUse(2L);
        verify(userTypeRepository).deleteById(2L);
    }

    @Test
    void findUserTypeById_WhenNotFound_ShouldThrow() {
        when(userTypeRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userTypeService.findUserTypeById(1L));
    }
}

