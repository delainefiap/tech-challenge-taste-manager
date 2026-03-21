package br.com.tastemanager.shared.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RestaurantRequestDTO {

    @JsonProperty("name")
    @NotBlank(message = "Name is mandatory.")
    private String name;

    @JsonProperty("address")
    @NotBlank(message = "Address is mandatory.")
    private String address;

    @JsonProperty("typeKitchen")
    @NotBlank(message = "Type Kitchen is mandatory.")
    private String typeKitchen;

    @JsonProperty("openingHours")
    @NotBlank(message = "Opening Hours is mandatory.")
    private String openingHours;

    @JsonProperty("ownerId")
    @NotNull(message = "Id of owner of the restaurant is mandatory.")
    private Long ownerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeKitchen() {
        return typeKitchen;
    }

    public void setTypeKitchen(String typeKitchen) {
        this.typeKitchen = typeKitchen;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }
}