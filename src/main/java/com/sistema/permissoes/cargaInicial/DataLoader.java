package com.sistema.permissoes.cargaInicial;

import com.sistema.permissoes.entidades.Perfil;
import com.sistema.permissoes.entidades.Permissao;
import com.sistema.permissoes.repository.PerfilRepository;
import com.sistema.permissoes.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final PermissaoRepository permissaoRepository;
    private final PerfilRepository perfilRepository;

    @Override
    public void run(String... args) {
        // Criar permissões
        List<String> permissoes = List.of("CONSULTAR", "EDITAR", "CADASTRAR", "EXCLUIR");
        for (String nome : permissoes) {
            permissaoRepository.findByNome(nome).orElseGet(() -> {
                Permissao p = new Permissao();
                p.setNome(nome);
                return permissaoRepository.save(p);
            });
        }

        // Criar perfis
        Perfil admin = perfilRepository.findByNome("ADMINISTRADOR").orElseGet(() -> {
            Perfil p = new Perfil();
            p.setNome("ADMINISTRADOR");
            p.setPermissoes(new HashSet<>(permissaoRepository.findAll())); // todas permissões
            return perfilRepository.save(p);
        });

        perfilRepository.findByNome("COLABORADOR").orElseGet(() -> {
            Perfil p = new Perfil();
            p.setNome("COLABORADOR");
            p.setPermissoes(Set.of(
                    permissaoRepository.findByNome("CONSULTAR").get(),
                    permissaoRepository.findByNome("EDITAR").get()
            ));
            return perfilRepository.save(p);
        });

        perfilRepository.findByNome("USUARIO").orElseGet(() -> {
            Perfil p = new Perfil();
            p.setNome("USUARIO");
            p.setPermissoes(Set.of(
                    permissaoRepository.findByNome("CONSULTAR").get()
            ));
            return perfilRepository.save(p);
        });
    }
}
