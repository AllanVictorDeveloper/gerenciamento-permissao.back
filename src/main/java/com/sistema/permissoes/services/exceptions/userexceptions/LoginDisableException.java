package com.sistema.permissoes.services.exceptions.userexceptions;

public class LoginDisableException extends RuntimeException {

    public LoginDisableException() {
        super("Login desativado, contate o administador do sistema.");
    }

    public LoginDisableException(String message) {
        super(message);
    }
}
