package com.example.library.controller;

import java.security.Principal;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.library.model.Usuario;
import com.example.library.service.UsuarioService;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UsuarioService usuarioService;

    public UserController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/edit")
    public String showEditForm(Model model, Principal principal) {
        String emailLogado = principal.getName();
        Optional<Usuario> opt = usuarioService.buscarPorEmail(emailLogado);
        if (opt.isEmpty()) {
            return "redirect:/login";
        }
        Usuario usuario = opt.get();
        model.addAttribute("usuario", usuario);
        return "edit_profile";
    }

    @PostMapping("/edit")
    public String updateUser(
            @ModelAttribute("usuario") Usuario formUsuario,
            BindingResult bindingResult,
            Model model,
            Principal principal) {
        String emailLogado = principal.getName();
        Optional<Usuario> optExisting = usuarioService.buscarPorEmail(emailLogado);
        if (optExisting.isEmpty()) {
            return "redirect:/login";
        }
        Usuario existing = optExisting.get();

        if (!formUsuario.getEmail().equalsIgnoreCase(existing.getEmail())) {
            if (usuarioService.existsByEmail(formUsuario.getEmail())) {
                model.addAttribute("emailError", "Já existe outra conta com este e-mail");
                model.addAttribute("usuario", existing);
                return "edit_profile";
            }
        }

        existing.setNome(formUsuario.getNome());
        existing.setEmail(formUsuario.getEmail());
        if (formUsuario.getSenha() != null && !formUsuario.getSenha().isEmpty()) {
            existing.setSenha(formUsuario.getSenha());
        }
        usuarioService.salvar(existing);

        return "redirect:/?updated";
    }

    @PostMapping("/delete")
    public String deleteUser(Principal principal, HttpServletRequest request) {
        String emailLogado = principal.getName();
        Optional<Usuario> optExisting = usuarioService.buscarPorEmail(emailLogado);
        if (optExisting.isEmpty()) {
            return "redirect:/login";
        }
        Usuario existing = optExisting.get();
        usuarioService.excluir(existing);

        request.getSession().invalidate();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            SecurityContextHolder.clearContext();
        }
        return "redirect:/login?deleted";
    }
}
