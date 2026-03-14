package br.com.tastemanager.shared.mapper;

import br.com.tastemanager.domain.entity.UserType;
import br.com.tastemanager.shared.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.shared.dto.response.UserTypeResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserTypeMapper {

    @Mapping(target = "name", source = "name")
    UserType toEntity(UserTypeRequestDTO dto);

    UserTypeRequestDTO toRequestDTO(UserType entity);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    UserTypeResponseDTO toResponseDTO(UserType userType);

    @Named("toResponseDTO")
    default UserTypeResponseDTO mapUserTypeToResponseDTO(UserType userType) {
        if (userType == null) {
            return null;
        }
        UserTypeResponseDTO responseDTO = new UserTypeResponseDTO();
        responseDTO.setId(userType.getId());
        responseDTO.setName(userType.getName());
        return responseDTO;
    }

}
