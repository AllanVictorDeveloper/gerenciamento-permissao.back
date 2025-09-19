package com.sistema.permissoes.controllers;

import com.sistema.permissoes.entidades.Perfil;
import com.sistema.permissoes.entidades.Permissao;
import com.sistema.permissoes.repository.PerfilRepository;
import com.sistema.permissoes.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/perfis")
@RequiredArgsConstructor
public class PerfilController {

    private final PerfilRepository perfilRepository;
    private final PermissaoRepository permissaoRepository;

    @GetMapping
    public List<Perfil> listarPerfis() {
        return perfilRepository.findAll();
    }

    @PostMapping
    public Perfil criarPerfil(@RequestBody Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @PutMapping("/{id}/permissoes")
    public Perfil atualizarPermissoes(@PathVariable Long id, @RequestBody List<String> permissoes) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil não encontrado"));

        Set<Permissao> novasPermissoes = permissoes.stream()
                .map(nome -> permissaoRepository.findByNome(nome)
                        .orElseThrow(() -> new RuntimeException("Permissão não encontrada: " + nome)))
                .collect(Collectors.toSet());

        perfil.setPermissoes(novasPermissoes);
        return perfilRepository.save(perfil);
    }
}

