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
        List<String> permissoes = List.of("Consultar", "Editar", "Cadastrar", "Excluir");
        for (String nome : permissoes) {
            permissaoRepository.findByNome(nome).orElseGet(() -> {
                Permissao p = new Permissao();
                p.setNome(nome);
                return permissaoRepository.save(p);
            });
        }

        // Criar perfis
        Perfil admin = perfilRepository.findByNome("Administrador").orElseGet(() -> {
            Perfil p = new Perfil();
            p.setNome("Administrador");
            p.setPermissoes(new HashSet<>(permissaoRepository.findAll())); // todas permissões
            return perfilRepository.save(p);
        });

        perfilRepository.findByNome("Colaborador").orElseGet(() -> {
            Perfil p = new Perfil();
            p.setNome("Colaborador");
            p.setPermissoes(Set.of(
                    permissaoRepository.findByNome("Consultar").get()
            ));
            return perfilRepository.save(p);
        });

        perfilRepository.findByNome("Usuario").orElseGet(() -> {
            Perfil p = new Perfil();
            p.setNome("Usuario");
            p.setPermissoes(Set.of(
                    permissaoRepository.findByNome("Consultar").get()
            ));
            return perfilRepository.save(p);
        });
    }
}
