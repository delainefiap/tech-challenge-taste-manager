package br.com.tastemanager.dto.response;

public class UserResponseDTO {

    private String name;
    private String email;
    private String login;

    private UserTypeIdResponseDTO userTypeId;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserTypeIdResponseDTO getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(UserTypeIdResponseDTO userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
