package com.sistema.permissoes.controllers;

import com.sistema.permissoes.entidades.Perfil;
import com.sistema.permissoes.entidades.Usuario;
import com.sistema.permissoes.repository.PerfilRepository;
import com.sistema.permissoes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    @PutMapping("/{id}/perfil")
    public Usuario atualizarPerfilUsuario(@PathVariable Long id, @RequestBody String nomePerfil) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Perfil perfil = perfilRepository.findByNome(nomePerfil)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        usuario.setPerfil(perfil);
        return usuarioRepository.save(usuario);
    }
}

