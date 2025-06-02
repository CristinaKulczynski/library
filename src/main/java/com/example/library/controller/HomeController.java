package com.example.library.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.library.model.Usuario;
import com.example.library.service.UsuarioService;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;

    public HomeController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {

        String emailLogado = principal.getName();

        Optional<Usuario> optUsuario = usuarioService.buscarPorEmail(emailLogado);
        if (optUsuario.isPresent()) {
            model.addAttribute("nomeUsuario", optUsuario.get().getNome());
        } else {
            model.addAttribute("nomeUsuario", emailLogado);
        }

        return "index";
    }
}
