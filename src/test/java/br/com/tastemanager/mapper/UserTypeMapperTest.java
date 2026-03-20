package br.com.tastemanager.mapper;

import br.com.tastemanager.domain.entity.UserType;
import br.com.tastemanager.shared.dto.response.UserTypeResponseDTO;
import br.com.tastemanager.shared.mapper.UserTypeMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserTypeMapperTest {

    private final UserTypeMapper mapper = Mappers.getMapper(UserTypeMapper.class);

    @Test
    void toResponseDTO_ShouldMapBasicFields() {
        UserType userType = new UserType();
        userType.setId(1L);
        userType.setName("ADMIN");
        userType.setDescription("desc");

        UserTypeResponseDTO dto = mapper.toResponseDTO(userType);

        assertEquals(1L, dto.getId());
        assertEquals("ADMIN", dto.getName());
        assertEquals("desc", dto.getDescription());
    }

    @Test
    void mapUserTypeToResponseDTO_ShouldMapFields() {
        UserType entity = new UserType();
        entity.setId(10L);
        entity.setName("ADMIN");

        UserTypeResponseDTO dto = mapper.mapUserTypeToResponseDTO(entity);

        assertEquals(10L, dto.getId());
        assertEquals("ADMIN", dto.getName());
    }

    @Test
    void toResponseDTO_WhenNull_ShouldReturnNull() {
        assertEquals(null, mapper.toResponseDTO(null));
    }

    @Test
    void toRequestDTO_WhenNull_ShouldReturnNull() {
        assertEquals(null, mapper.toRequestDTO(null));
    }
}
