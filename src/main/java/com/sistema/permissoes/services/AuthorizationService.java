package com.sistema.permissoes.services;



import com.sistema.permissoes.dto.request.auth.LoginRequestDto;
import com.sistema.permissoes.dto.request.auth.MudarSenhaRequestDto;
import com.sistema.permissoes.dto.response.auth.LoginResponseDto;
import com.sistema.permissoes.dto.response.auth.TokenResponseDto;
import com.sistema.permissoes.entidades.Usuario;
import com.sistema.permissoes.exceptions.UnauthorizedException;
import com.sistema.permissoes.repository.UsuarioRepository;
import com.sistema.permissoes.security.TokenService;
import com.sistema.permissoes.services.exceptions.BadRequestException;
import com.sistema.permissoes.services.exceptions.userexceptions.LoginDisableException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthorizationService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(AuthorizationService.class);

    @Value("${api.jwt.token.expiration}")
    private Integer expirationToken;

    @Value("${api.jwt.refresh-token.expiration}")
    private Integer expirationRefreshToken;

    @Autowired
    private UsuarioRepository iUsuarioRepository;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.iUsuarioRepository.findByEmail(username);
    }


    @Transactional(rollbackFor = Exception.class)
    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletRequest request) {

        if (!this.iUsuarioRepository.existsUsuarioByEmail(requestDto.getLogin()))
            throw new BadRequestException("Usuário inexistente ou senha inválida.");


        var usernamePassword = new UsernamePasswordAuthenticationToken(requestDto.getLogin(), requestDto.getSenha());

        var auth = this.authenticationManager.authenticate(usernamePassword);


        var token = tokenService.generateToken((Usuario) auth.getPrincipal(), expirationToken);
        var refreshToken = tokenService.generateToken((Usuario) auth.getPrincipal(), expirationRefreshToken);


        Usuario user = this.iUsuarioRepository.save(((Usuario) auth.getPrincipal()));

        return LoginResponseDto
                .builder()
                .token(token)
                .refreshToken(refreshToken)
                .versao("1.0.0")
                .build();

    }



    public TokenResponseDto getRefreshToken(String refreshTokenRequest) {

        String username = this.tokenService.validateToken(refreshTokenRequest);
        if (username.isEmpty()) {
            throw new UnauthorizedException("Sessão expirada.");
        }

        UserDetails user = iUsuarioRepository.findByEmail(username);


        var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var token = tokenService.generateToken((Usuario) user, expirationToken);
        var refreshToken = tokenService.generateToken((Usuario) user, expirationRefreshToken);

        return TokenResponseDto
                .builder()
                .token(token)
                .refreshToken(refreshToken)
                .build();
    }


}
