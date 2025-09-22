package com.sistema.permissoes.services;

import com.sistema.permissoes.dto.request.auth.UsuarioRequestDto;
import com.sistema.permissoes.dto.request.auth.UsuarioResponseDto;
import com.sistema.permissoes.entidades.Perfil;
import com.sistema.permissoes.entidades.Usuario;
import com.sistema.permissoes.repository.PerfilRepository;
import com.sistema.permissoes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;


    @Transactional(rollbackFor = Exception.class)
    public String cadastrarUsuario(UsuarioRequestDto usuarioRequestDto) {

        Optional<Perfil> perfil = this.perfilRepository.findByNome(usuarioRequestDto.getNome());


        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioRequestDto.getEmail());
        usuario.setNome(usuarioRequestDto.getNome());
        usuario.setSenha(new BCryptPasswordEncoder().encode("SEPLAG@2025"));
        usuario.setPerfil(perfil.get());

        this.usuarioRepository.save(usuario);

        String mensagem = "Usuário cadastrado com sucesso!";

        return mensagem;
    }


    public List<UsuarioResponseDto> buscarUsuarios() {
        List<Usuario> usuarios = this.usuarioRepository.findAll();

        // Mapeia cada usuário para o DTO
        List<UsuarioResponseDto> usuarioResponseDtos = usuarios.stream()
                .map(usuario -> {
                    UsuarioResponseDto dto = new UsuarioResponseDto();
                    dto.setId(usuario.getId());
                    dto.setNome(usuario.getNome());
                    dto.setEmail(usuario.getEmail());
                    dto.setPerfil(usuario.getPerfil().getNome());
                    return dto;
                })
                .toList();

        return usuarioResponseDtos;
    }

}
