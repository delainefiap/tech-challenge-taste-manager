package br.com.tastemanager.shared.dto.response;

public class MenuResponseDTO {

    private Long id;
    private Long restaurantItemNumber;
    private String name;
    private String description;
    private Double price;
    private String imagePath;
    private Boolean availableOnlyAtRestaurant;
    private RestaurantResponseDTO restaurant;

    public MenuResponseDTO() {
    }

    public MenuResponseDTO(Long id, Long restaurantItemNumber, String name, String description, Double price,
                           String imagePath, Boolean availableOnlyAtRestaurant,
                           RestaurantResponseDTO restaurant) {
        this.id = id;
        this.restaurantItemNumber = restaurantItemNumber;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imagePath = imagePath;
        this.availableOnlyAtRestaurant = availableOnlyAtRestaurant;
        this.restaurant = restaurant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantItemNumber() {
        return restaurantItemNumber;
    }

    public void setRestaurantItemNumber(Long restaurantItemNumber) {
        this.restaurantItemNumber = restaurantItemNumber;
    }

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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Boolean getAvailableOnlyAtRestaurant() {
        return availableOnlyAtRestaurant;
    }

    public void setAvailableOnlyAtRestaurant(Boolean availableOnlyAtRestaurant) {
        this.availableOnlyAtRestaurant = availableOnlyAtRestaurant;
    }

    public RestaurantResponseDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantResponseDTO restaurant) {
        this.restaurant = restaurant;
    }
}
