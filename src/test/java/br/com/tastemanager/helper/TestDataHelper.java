package br.com.tastemanager.helper;

import br.com.tastemanager.dto.request.ChangePasswordRequestDTO;
import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.entity.User;
import br.com.tastemanager.entity.UserType;

public class TestDataHelper {
    public static UserRequestDTO buildValidUserRequest() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setName("John");
        dto.setEmail("john@example.com");
        dto.setLogin("johndoe");
        dto.setPassword("pass");
        dto.setAddress("123 St");
        dto.setUserTypeId(buildValidUserType());
        return dto;
    }

    public static UserUpdateRequestDTO buildValidUserUpdateRequest() {
        UserUpdateRequestDTO dto = new UserUpdateRequestDTO();
        dto.setName("Jane");
        dto.setEmail("jane@example.com");
        dto.setAddress("Street 123");
        dto.setUserTypeId(buildValidUserType());
        return dto;
    }

    public static ChangePasswordRequestDTO buildChangePasswordRequest(String oldPass, String newPass) {
        ChangePasswordRequestDTO dto = new ChangePasswordRequestDTO();
        dto.setOldPassword(oldPass);
        dto.setNewPassword(newPass);
        return dto;
    }

    public static UserType buildValidUserType() {
        UserType userType = new UserType();
        userType.setId(1L);
        userType.setName("BASIC");
        return userType;
    }

    public static User buildValidUser() {
        User user = new User();
        user.setId(1L);
        user.setName("John");
        user.setEmail("john@example.com");
        user.setLogin("johndoe");
        user.setPassword("pass");
        user.setAddress("123 St");
        user.setUserTypeId(buildValidUserType());
        return user;
    }

    public static User buildUpdatedUser() {
        User user = new User();
        user.setId(1L);
        user.setName("Jane");
        user.setEmail("jane@example.com");
        user.setLogin("johndoe");
        user.setPassword("pass");
        user.setAddress("Street 123");
        user.setUserTypeId(buildValidUserType());
        return user;
    }
}