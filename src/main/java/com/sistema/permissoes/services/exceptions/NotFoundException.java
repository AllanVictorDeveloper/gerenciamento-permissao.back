package com.sistema.permissoes.services.exceptions;

public class NotFoundException extends RuntimeException {

//    public NotFoundException() {
//        super("'Senha' e 'confirma Senha' não são iguais.");
//    }

    public NotFoundException(String message) {
        super(message);
    }
}
