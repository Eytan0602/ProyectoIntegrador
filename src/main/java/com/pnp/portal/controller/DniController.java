package com.pnp.portal.controller;

import com.pnp.portal.model.SolicitudDni;
import com.pnp.portal.repository.SolicitudDniRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DniController {

    @Autowired
    private SolicitudDniRepository solicitudDniRepository;

    @GetMapping("/dni")
    public String mostrarFormulario(Model model) {
        model.addAttribute("solicitud", new SolicitudDni());
        return "dni";
    }

    @PostMapping("/dni")
    public String procesarFormulario(@ModelAttribute SolicitudDni solicitud, HttpSession session, Model model) {
        String captchaReal = (String) session.getAttribute("captcha");

        if (captchaReal == null || !captchaReal.equalsIgnoreCase(solicitud.getCaptcha())) {
            model.addAttribute("errorCaptcha", "Código CAPTCHA incorrecto. Intenta de nuevo.");
            model.addAttribute("solicitud", solicitud);
            return "dni";
        }

        // GUARDAR EN BASE DE DATOS
        try {
            solicitudDniRepository.save(solicitud);
            System.out.println("✅ DNI GUARDADO: " + solicitud.getDni());
        } catch (Exception e) {
            System.err.println("❌ ERROR AL GUARDAR DNI: " + e.getMessage());
            e.printStackTrace();
        }

        session.setAttribute("dni", solicitud.getDni());
        return "redirect:/licencia";
    }
}