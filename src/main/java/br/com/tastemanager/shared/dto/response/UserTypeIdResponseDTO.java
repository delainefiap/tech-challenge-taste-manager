package br.com.tastemanager.shared.dto.response;

public class UserTypeIdResponseDTO {
    private String name;

    public UserTypeIdResponseDTO() {}
    public UserTypeIdResponseDTO(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
