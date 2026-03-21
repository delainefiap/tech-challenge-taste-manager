package br.com.tastemanager.shared.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "DTO para alteração de senha do usuário")
public class ChangePasswordRequestDTO {

    @Schema(description = "Senha atual do usuário", example = "senhaAtual123", required = true)
    private String oldPassword;

    @NotBlank(message = "The new password cannot be empty or null.")
    @Schema(description = "Nova senha do usuário", example = "novaSenhaSegura456", required = true)
    private String newPassword;


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
