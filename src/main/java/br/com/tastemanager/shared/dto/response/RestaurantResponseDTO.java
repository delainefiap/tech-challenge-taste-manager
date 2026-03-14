package br.com.tastemanager.shared.dto.response;

public class RestaurantResponseDTO {

    private Long id;
    private String name;
    private String address;
    private String typeKitchen;
    private String openingHours;
    private String ownerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }
}