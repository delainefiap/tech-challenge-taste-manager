package br.com.tastemanager.shared.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados de requisição para operações de tipo de usuário")
public class UserTypeRequestDTO {

    @Schema(description = "Nome do tipo de usuário", example = "Administrador")
    @NotBlank(message = "Nome do tipo de usuário é obrigatório")
    @Size(min = 2, max = 50, message = "Nome deve ter entre 2 e 50 caracteres")
    private String name;

    @Schema(description = "Descrição do tipo de usuário", example = "Usuário com acesso total ao sistema")
    @Size(max = 200, message = "Descrição deve ter no máximo 200 caracteres")
    private String description;

    public UserTypeRequestDTO() {}

    public UserTypeRequestDTO(String name, String description) {
        this.name = name;
        this.description = description;
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

    @Override
    public String toString() {
        return "UserTypeRequestDTO{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
