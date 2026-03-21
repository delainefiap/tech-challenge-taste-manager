package br.com.tastemanager.shared.dto.response;

public class MenuCreateResponseDTO {

    private Long restaurantItemNumber;

    public MenuCreateResponseDTO() {
    }

    public MenuCreateResponseDTO(Long restaurantItemNumber) {
        this.restaurantItemNumber = restaurantItemNumber;
    }

    public Long getRestaurantItemNumber() {
        return restaurantItemNumber;
    }

    public void setRestaurantItemNumber(Long restaurantItemNumber) {
        this.restaurantItemNumber = restaurantItemNumber;
    }
}
