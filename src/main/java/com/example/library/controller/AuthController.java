package com.example.library.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.library.model.Usuario;
import com.example.library.service.UsuarioService;

import jakarta.validation.Valid;


@Controller
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }


    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
 
            return "redirect:/";
        }
        return "login";
    }

    // Página de registro
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("usuario") Usuario usuario,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (usuarioService.existsByEmail(usuario.getEmail())) {
            model.addAttribute("emailError", "Já existe uma conta com este e-mail");
            return "register";
        }

        usuarioService.salvar(usuario);
        return "redirect:/login?registered";
    }
}
