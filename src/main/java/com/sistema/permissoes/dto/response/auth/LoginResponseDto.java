package com.sistema.permissoes.dto.response.auth;


import lombok.Builder;

@Builder
public record LoginResponseDto(String token, String refreshToken,String versao) {


}
