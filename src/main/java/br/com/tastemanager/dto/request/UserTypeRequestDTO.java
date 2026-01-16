package br.com.tastemanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO para requisições de criação e atualização de tipos de usuário.
 */
@Schema(description = "Dados de requisição para operações de tipo de usuário")
public class UserTypeRequestDTO {

    @Schema(description = "Nome do tipo de usuário", example = "Administrador")
    @NotBlank(message = "Nome do tipo de usuário é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String name;

    @Schema(description = "Descrição do tipo de usuário", example = "Usuário com acesso total ao sistema")
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    private String description;

    // Constructors
    public UserTypeRequestDTO() {}

    public UserTypeRequestDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
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

    @Override
    public String toString() {
        return "UserTypeRequestDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
