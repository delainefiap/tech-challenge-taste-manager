package br.com.tastemanager.helper;

import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestDataHelper {
//    UserMapper userMapper = Mappers.getMapper(UserMapper.class);
//
//    @BeforeEach
//    void setUp() {
//        userMapper = Mappers.getMapper(UserMapper.class);
//    }
//
//    public static UserRequestDTO buildValidUserRequest() {
//        UserRequestDTO dto = new UserRequestDTO();
//        dto.setName("John");
//        dto.setEmail("john@example.com");
//        dto.setLogin("johndoe");
//        dto.setPassword("pass");
////        dto.setTypePerson("customer");
//        dto.setAddress("123 St");
//        return dto;
//    }
//
//    public static UserUpdateRequestDTO buildValidUserUpdateRequest() {
//        UserUpdateRequestDTO dto = new UserUpdateRequestDTO();
//        dto.setName("Jane");
//        dto.setEmail("jane@example.com");
//        dto.setTypePerson("customer");
//        dto.setAddress("Street 123");
//        return dto;
//    }
//
//    public static ChangePasswordRequestDTO buildChangePasswordRequest(String oldPass, String newPass) {
//        ChangePasswordRequestDTO dto = new ChangePasswordRequestDTO();
//        dto.setOldPassword(oldPass);
//        dto.setNewPassword(newPass);
//        return dto;
//    }
//
//    @Test
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
}