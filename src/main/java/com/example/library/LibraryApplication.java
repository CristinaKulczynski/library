package com.example.library;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.library.model.Usuario;
import com.example.library.service.UsuarioService;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }


    @Bean
    public CommandLineRunner initAdmin(UsuarioService usuarioService) {
        return args -> {
            String adminEmail = "admin@library.com";
            if (!usuarioService.existsByEmail(adminEmail)) {
                Usuario admin = new Usuario();
                admin.setNome("Administrador");
                admin.setEmail(adminEmail);
                admin.setSenha("admin123");
                admin.setRole("ROLE_ADMIN");
                usuarioService.salvar(admin);
                System.out.println("Usuário admin criado: " + adminEmail + " / admin123");
            }
        };
    }
}
