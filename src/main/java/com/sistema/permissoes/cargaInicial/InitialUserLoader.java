package com.sistema.permissoes.cargaInicial;

import com.sistema.permissoes.entidades.Perfil;
import com.sistema.permissoes.entidades.Usuario;
import com.sistema.permissoes.repository.PerfilRepository;
import com.sistema.permissoes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InitialUserLoader implements ApplicationRunner {

    private final UsuarioRepository userRepository;
    private final PerfilRepository perfilRepository;

    @Autowired
    public InitialUserLoader(UsuarioRepository userRepository, PerfilRepository perfilRepository) {
        this.userRepository = userRepository;
        this.perfilRepository = perfilRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        String username = "allanvictor.developer@gmail.com";


        if (!this.userRepository.existsUsuarioByEmail(username)) {
            var perfil = this.perfilRepository.findById(1L);
            Usuario usuario = new Usuario();
            usuario.setEmail("allanvictor.developer@gmail.com");
            usuario.setNome("Allan");
            usuario.setSenha("$2a$10$trjlj90ToHtr/eYEeyU8yeO2KInIL3MP8OgZN4gvwV3TmLYcxajv2");
            usuario.setPerfil(perfil.get());
            this.userRepository.save(usuario);
        }
    }
}
