package br.com.tastemanager.shared.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class MenuRequestDTO {

    @NotBlank(message = "The name cannot be blank")
    private String name;

    @NotBlank(message = "You should provide a description")
    private String description;

    @NotNull(message = "The price must not be nul")
    @Positive(message = "The price must be a positive value")
    private Double price;

    @JsonProperty("availableOnlyAtRestaurant")
    private Boolean availableOnlyAtRestaurant = false;

    private String imagePath;

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

    public void setAvailableOnlyAtRestaurant(Boolean availableOnlyAtRestaurant) {
        this.availableOnlyAtRestaurant = availableOnlyAtRestaurant;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}