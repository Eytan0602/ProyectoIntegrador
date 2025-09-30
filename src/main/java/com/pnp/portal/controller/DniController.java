package com.pnp.portal.controller;

import com.pnp.portal.model.SolicitudDni;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DniController {

    @GetMapping("/dni")
    public String mostrarFormulario(Model model) {
        model.addAttribute("solicitud", new SolicitudDni());
        return "dni"; // Thymeleaf template "dni.html"
    }

    @PostMapping("/dni")
    public String procesarFormulario(@ModelAttribute SolicitudDni solicitud, HttpSession session, Model model) {
        String captchaReal = (String) session.getAttribute("captcha");

        if (captchaReal == null || !captchaReal.equalsIgnoreCase(solicitud.getCaptcha())) {
            model.addAttribute("errorCaptcha", "Código CAPTCHA incorrecto. Intenta de nuevo.");
            model.addAttribute("solicitud", solicitud); // Mantener datos en el formulario
            return "dni";
        }

        // CAPTCHA correcto: continuar lógica de negocio, guardar datos, etc.
        model.addAttribute("solicitud", solicitud);
        return "licencia"; // página siguiente
    }
}
