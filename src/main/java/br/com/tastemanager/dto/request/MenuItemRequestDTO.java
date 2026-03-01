package br.com.tastemanager.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MenuItemRequestDTO {

    @NotBlank(message = "The name cannot be blank")
    private String name;

    @NotBlank(message = "You should provide a description")
    private String description;

    @NotNull(message = "The price must not be null")
    @Min(value = 0, message = "The price must be a positive value")
    private Double price;

    @JsonProperty("availableOnlyAtRestaurant")
    private Boolean availableOnlyAtRestaurant;
    private String photoPath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAvailableOnlyAtRestaurant() {
        return availableOnlyAtRestaurant;
    }

    public boolean isAvailableOnlyAtRestaurant() {
        return availableOnlyAtRestaurant != null ? availableOnlyAtRestaurant : false;
    }

    public void setAvailableOnlyAtRestaurant(Boolean availableOnlyAtRestaurant) {
        this.availableOnlyAtRestaurant = availableOnlyAtRestaurant;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}