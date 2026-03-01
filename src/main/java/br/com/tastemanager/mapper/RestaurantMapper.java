package br.com.tastemanager.mapper;

import br.com.tastemanager.dto.request.RestaurantRequestDTO;
import br.com.tastemanager.dto.response.RestaurantResponseDTO;
import br.com.tastemanager.entity.Restaurant;
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