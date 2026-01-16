package br.com.tastemanager.mapper;

import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.entity.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserMapperTest {

//    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);
//
//    void shouldMapUserRequestDtoToEntity() {
//        UserRequestDTO dto = new UserRequestDTO();
//        dto.setName("John Doe");
//        dto.setEmail("john.doe@example.com");
//        dto.setLogin("johndoe");
//        dto.setPassword("password123");
////        dto.setTypePerson("Individual");
//        dto.setAddress("123 Main St");
//
//        User user = userMapper.UserRequestDtoToEntity(dto);
//
//        assertNull(user.getId());
//        assertEquals("John Doe", user.getName());
//        assertEquals("john.doe@example.com", user.getEmail());
//        assertEquals("johndoe", user.getLogin());
//        assertEquals("password123", user.getPassword());
//        assertEquals("Individual", user.getTypePerson());
//        assertEquals("123 Main St", user.getAddress());
//    }
//
//    @Test
//    void shouldMapUserUpdateRequestDtoToEntity() {
//        UserUpdateRequestDTO dto = new UserUpdateRequestDTO();
//        dto.setName("Jane Doe");
//        dto.setEmail("jane.doe@example.com");
//        dto.setTypePerson("Individual");
//        dto.setAddress("456 Elm St");
//
//        User user = userMapper.userUpdateRequestDtoToEntity(dto);
//
//        assertNull(user.getId());
//        assertEquals("Jane Doe", user.getName());
//        assertEquals("jane.doe@example.com", user.getEmail());
//        assertEquals("Individual", user.getTypePerson());
//        assertEquals("456 Elm St", user.getAddress());
//    }
//
//    @Test
//    void shouldMapUserToUserResponseDto() {
//        User user = new User();
//        user.setName("John Doe");
//        user.setEmail("john.doe@example.com");
//        user.setLogin("johndoe");
//        user.setTypePerson("Individual");
//        user.setAddress("123 Main St");
//
//        UserResponseDTO dto = userMapper.userToUserResponseDto(user);
//
//        assertEquals("John Doe", dto.getName());
//        assertEquals("john.doe@example.com", dto.getEmail());
//        assertEquals("johndoe", dto.getLogin());
//        assertEquals("Individual", dto.getTypePerson());
//        assertEquals("123 Main St", dto.getAddress());
//    }
}