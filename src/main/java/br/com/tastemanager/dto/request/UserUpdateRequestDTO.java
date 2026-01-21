package br.com.tastemanager.dto.request;

import br.com.tastemanager.entity.UserType;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para atualização de dados do usuário")
public class UserUpdateRequestDTO {

    @JsonProperty("name")
    @NotBlank(message = "Name cannot be blank.")
    private String name;

    @JsonProperty("email")
    @NotBlank(message = "The email must be valid and cannot be blank.")
    @Email(message = "The email must be valid and cannot be blank.")
    private String email;

    @JsonProperty("userTypeId")
    private UserType userTypeId;

    @JsonProperty("address")
    private String address;

    @JsonProperty("typePerson")
    private String typePerson;

    @JsonAnySetter
    public void handleUnknownField(String key, Object value) {
        throw new IllegalArgumentException("It is not possible to change this field or it doesn't exist : " + key);
    }

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

    public UserType getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(UserType userTypeId) {
        this.userTypeId = userTypeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypePerson() {
        return typePerson;
    }

    public void setTypePerson(String typePerson) {
        this.typePerson = typePerson;
    }
}
