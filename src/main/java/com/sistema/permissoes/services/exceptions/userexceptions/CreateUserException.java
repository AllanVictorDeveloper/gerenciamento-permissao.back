package com.sistema.permissoes.services.exceptions.userexceptions;

import java.util.Arrays;
import java.util.List;

public class CreateUserException extends RuntimeException {

    public CreateUserException(List<String> message) {
        super(Arrays.toString(message.toArray()));
    }
}
