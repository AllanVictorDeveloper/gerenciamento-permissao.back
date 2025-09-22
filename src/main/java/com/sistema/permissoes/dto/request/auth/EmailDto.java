package com.sistema.permissoes.dto.request.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDto {

    @NotEmpty(message = "Preenchimento Obrigatório.")
    private String email;

    private Boolean masterSolicitou;
}
