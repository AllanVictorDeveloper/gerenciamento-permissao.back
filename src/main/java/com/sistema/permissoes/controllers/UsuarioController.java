package com.sistema.permissoes.controllers;

import com.sistema.permissoes.dto.request.auth.LoginRequestDto;
import com.sistema.permissoes.dto.request.auth.UsuarioRequestDto;
import com.sistema.permissoes.dto.request.auth.UsuarioResponseDto;
import com.sistema.permissoes.dto.response.auth.LoginResponseDto;
import com.sistema.permissoes.entidades.Perfil;
import com.sistema.permissoes.entidades.Usuario;
import com.sistema.permissoes.repository.PerfilRepository;
import com.sistema.permissoes.repository.UsuarioRepository;
import com.sistema.permissoes.services.UsuarioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final UsuarioService usuarioService;

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


    @PostMapping("/cadastrar")
    public ResponseEntity<Object> cadastraUsuario(
            @Valid @RequestBody UsuarioRequestDto request) throws Exception {

        try {
            String result = this.usuarioService.cadastrarUsuario(request);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }


    @GetMapping("/buscar-todos")
    public ResponseEntity<List<UsuarioResponseDto>> buscarTodosUsuarios() throws Exception {


        List<UsuarioResponseDto> result = this.usuarioService.buscarUsuarios();

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result);


    }
}

