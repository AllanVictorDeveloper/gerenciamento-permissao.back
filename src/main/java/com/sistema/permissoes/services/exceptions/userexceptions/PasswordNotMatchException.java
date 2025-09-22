package com.sistema.permissoes.services.exceptions.userexceptions;

public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException() {
        super("'Senha' e 'confirma Senha' não são iguais.");
    }

    public PasswordNotMatchException(String message) {
        super(message);
    }
}
