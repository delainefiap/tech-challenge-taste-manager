package br.com.tastemanager.mapper;

import br.com.tastemanager.dto.request.UserRequestDTO;
import br.com.tastemanager.dto.request.UserUpdateRequestDTO;
import br.com.tastemanager.dto.response.UserResponseDTO;
import br.com.tastemanager.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "login", source = "login")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "userTypeId", source = "userTypeId")
    @Mapping(target = "address", source = "address")
    User UserRequestDtoToEntity(UserRequestDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "lastUpdate", ignore = true)
    @Mapping(target = "userTypeId", source = "userTypeId")
    @Mapping(target = "address", source = "address")
    User userUpdateRequestDtoToEntity(UserUpdateRequestDTO dto);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "login", source = "login")
    @Mapping(target = "userTypeId", expression = "java(new br.com.tastemanager.dto.response.UserTypeIdResponseDTO(user.getUserTypeId().getName()))")
    @Mapping(target = "address", source = "address")
    @Mapping(target = "lastUpdate", source = "lastUpdate")
    UserResponseDTO userToUserResponseDto(User user);

}
