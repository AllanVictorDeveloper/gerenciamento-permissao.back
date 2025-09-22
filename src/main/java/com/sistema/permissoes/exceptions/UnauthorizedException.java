package com.sistema.permissoes.exceptions;

public class UnauthorizedException extends RuntimeException {

//    public UnauthorizedException() {
//        super("Usuário não autorizado a realizar essa operação.");
//    }

    public UnauthorizedException(String message) {
        super(message);
    }
}
