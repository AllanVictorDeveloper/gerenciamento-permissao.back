package com.sistema.permissoes.security;

import com.sistema.permissoes.services.AuthorizationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {


    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthorizationService authorizationService;


    private HandlerExceptionResolver handlerExceptionResolver;


    @Autowired
    public SecurityFilter(HandlerExceptionResolver handlerExceptionResolver) {
        this.handlerExceptionResolver = handlerExceptionResolver;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
//        System.out.println("SecurityFilter foi invocado.");

        var token = this.recoverToken(request);

        try {

            if (token != null) {

                String subject = tokenService.validateToken(token);

                if (!subject.isEmpty()) {
                    UserDetails user = authorizationService.loadUserByUsername(subject);
                    if (user != null) {
                        var authorities = user.getAuthorities(); // Verifique as authorities
                        var authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    } else {
                        throw new AccessDeniedException("Usuário não encontrado.");
                    }
                } else {
                    throw new AccessDeniedException("Token expirado.");
                }
            }

            filterChain.doFilter(request, response);

        } catch (AccessDeniedException ex) {
            handlerExceptionResolver.resolveException(request, response, null, ex);
        }


    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");

        if (authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }

}