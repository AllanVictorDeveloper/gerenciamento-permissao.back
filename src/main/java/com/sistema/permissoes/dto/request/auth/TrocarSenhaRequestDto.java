package com.sistema.permissoes.dto.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrocarSenhaRequestDto {


    @NotEmpty(message = "Preenchimento Obrigatório.")
    private String senhaAntiga;

    @NotEmpty(message = "Preenchimento Obrigatório.")
    @Length(min = 8, max = 30, message = "A senha deve ter entre 8 e 30 caracteres")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).*$",
            message = "A senha deve incluir ao menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.")
    @Schema(example = "string")
    private String senhaNova;
}
