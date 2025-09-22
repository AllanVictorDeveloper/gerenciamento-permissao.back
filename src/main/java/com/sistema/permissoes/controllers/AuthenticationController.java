package com.sistema.permissoes.controllers;

import com.sistema.permissoes.dto.request.auth.LoginRequestDto;
import com.sistema.permissoes.dto.response.auth.LoginResponseDto;
import com.sistema.permissoes.services.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private HttpServletRequest requestHeader;

    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @Valid @RequestBody LoginRequestDto request, HttpServletRequest httpServletRequest) throws Exception {

        try {
            LoginResponseDto result = this.authorizationService.login(request, httpServletRequest);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        } catch (RuntimeException e) {

            log.error(e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}
