package com.pnp.portal.controller;

import com.pnp.portal.model.Usuario;
import com.pnp.portal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // MOSTRAR LOGIN
    @GetMapping("/login")
    public String loginPage(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registro", required = false) String registro,
            Model model) {

        if (error != null) {
            model.addAttribute("errorMsg", "DNI o contraseña incorrectos");
        }

        if (logout != null) {
            model.addAttribute("logoutMsg", "Sesión cerrada correctamente");
        }

        if (registro != null) {
            model.addAttribute("successMsg", "Registro exitoso. Ahora puedes iniciar sesión");
        }

        return "login";
    }

    // MOSTRAR REGISTRO
    @GetMapping("/registro")
    public String registroPage(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    // PROCESAR REGISTRO
    @PostMapping("/registro")
    public String processRegistro(
            @RequestParam String dni,
            @RequestParam String nombreCompleto,
            @RequestParam String email,
            @RequestParam String telefono,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model) {

        // Validaciones
        if (!password.equals(confirmPassword)) {
            model.addAttribute("errorMsg", "Las contraseñas no coinciden");
            return "registro";
        }

        if (password.length() < 6) {
            model.addAttribute("errorMsg", "La contraseña debe tener al menos 6 caracteres");
            return "registro";
        }

        if (usuarioRepository.existsByDni(dni)) {
            model.addAttribute("errorMsg", "El DNI ya está registrado");
            return "registro";
        }

        if (usuarioRepository.existsByEmail(email)) {
            model.addAttribute("errorMsg", "El email ya está registrado");
            return "registro";
        }

        // Crear usuario
        Usuario u = new Usuario();
        u.setDni(dni);
        u.setNombreCompleto(nombreCompleto);
        u.setEmail(email);
        u.setTelefono(telefono);
        u.setPassword(passwordEncoder.encode(password));

        usuarioRepository.save(u);

        return "redirect:/login?registro=exitoso";
    }
}