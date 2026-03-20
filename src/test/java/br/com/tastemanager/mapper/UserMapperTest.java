package br.com.tastemanager.mapper;

import br.com.tastemanager.shared.dto.request.UserRequestDTO;
import br.com.tastemanager.shared.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.shared.dto.response.UserResponseDTO;
import br.com.tastemanager.domain.entity.User;
import br.com.tastemanager.domain.entity.UserType;
import br.com.tastemanager.shared.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapUserRequestDtoToEntity() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("John Doe");
        dto.setEmail("john.doe@example.com");
        dto.setLogin("johndoe");
        dto.setPassword("password123");
        UserType ut = new UserType();
        ut.setId(1L);
        ut.setName("Admin");
        dto.setUserTypeId(ut);
        dto.setAddress("123 Main St");

        User user = userMapper.UserRequestDtoToEntity(dto);

        assertNull(user.getId());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("johndoe", user.getLogin());
        assertEquals("password123", user.getPassword());
        assertNull(user.getUserTypeId());
        assertEquals("123 Main St", user.getAddress());
        assertNull(user.getCreatedAt());
        assertNull(user.getLastUpdate());
    }

    @Test
    void shouldMapUserUpdateRequestDtoToEntity() {
        UserUpdateRequestDTO dto = new UserUpdateRequestDTO();
        dto.setName("Jane Doe");
        dto.setEmail("jane.doe@example.com");
        dto.setAddress("456 Elm St");

        User user = userMapper.userUpdateRequestDtoToEntity(dto);

        assertNull(user.getId());
        assertEquals("Jane Doe", user.getName());
        assertEquals("jane.doe@example.com", user.getEmail());
        assertEquals("456 Elm St", user.getAddress());
    }

    @Test
    void shouldMapUserToUserResponseDto() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setLogin("johndoe");
        user.setAddress("123 Main St");
        UserType ut2 = new UserType();
        ut2.setId(2L);
        ut2.setName("Customer");
        user.setUserTypeId(ut2);

        UserResponseDTO dto = userMapper.userToUserResponseDto(user);

        assertEquals("John Doe", dto.getName());
        assertEquals("john.doe@example.com", dto.getEmail());
        assertEquals("johndoe", dto.getLogin());
        assertEquals("123 Main St", dto.getAddress());
        assertNotNull(dto.getUserTypeId());
        assertEquals("Customer", dto.getUserTypeId().getName());
    }

    @Test
    void userUpdateRequestDtoToEntity_WhenNull_ShouldReturnNull() {
        User user = userMapper.userUpdateRequestDtoToEntity(null);

        assertNull(user);
    }
}