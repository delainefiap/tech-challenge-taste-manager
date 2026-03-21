package br.com.tastemanager.shared.mapper;

import br.com.tastemanager.domain.entity.Restaurant;
import br.com.tastemanager.shared.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.shared.dto.response.RestaurantResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {


    @Mapping(target = "ownerName", source = "owner.name")
    RestaurantResponseDTO toResponseDTO(Restaurant restaurant);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "owner", ignore = true)
    Restaurant toEntity(RestaurantRequestDTO requestDTO);
}