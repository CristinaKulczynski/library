package com.example.library.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.library.model.Usuario;
import com.example.library.repository.UsuarioRepository;

public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService() {
        this.usuarioRepository = ApplicationContextProvider.getBean(UsuarioRepository.class);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + email));

        return new User(
            usuario.getEmail(),
            usuario.getSenha(),
            Collections.singletonList(new SimpleGrantedAuthority(usuario.getRole()))
        );
    }
}
