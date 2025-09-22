package com.sistema.permissoes.services.exceptions;

public class BadRequestException extends RuntimeException {

//    public NotFoundException() {
//        super("'Senha' e 'confirma Senha' não são iguais.");
//    }

    public BadRequestException(String message) {
        super(message);
    }
}
