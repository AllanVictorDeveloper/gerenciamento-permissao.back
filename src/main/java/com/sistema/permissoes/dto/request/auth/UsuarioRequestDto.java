package com.sistema.permissoes.dto.request.auth;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRequestDto {

    @NotEmpty(message="Preenchimento Obrigatório.")
    @Length(max=100, message="Este campo deve ter no máximo 100 caracteres.")
    private String nome;

    @NotEmpty(message="Preenchimento Obrigatório.")
    @Length(max=200, message="Este campo deve ter no máximo 200 caracteres.")
    private String email;


    @NotEmpty(message="Preenchimento Obrigatório.")
    @Length(max=200, message="Este campo deve ter no máximo 200 caracteres.")
    private String perfil;





}
