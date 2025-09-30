package com.pnp.portal.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class LicenciaController {

    @GetMapping("/licencia")
    public String mostrarFormularioLicencia(HttpSession session, Model model) {
        // Pasar datos guardados en sesión al modelo para el formulario
        model.addAttribute("dni", session.getAttribute("dni"));
        model.addAttribute("nombre_completo", session.getAttribute("nombre_completo"));
        model.addAttribute("numero_licencia", session.getAttribute("numero_licencia"));
        model.addAttribute("categoria", session.getAttribute("categoria"));
        model.addAttribute("fecha_expedicion", session.getAttribute("fecha_expedicion"));
        model.addAttribute("fecha_vencimiento", session.getAttribute("fecha_vencimiento"));
        model.addAttribute("estado", session.getAttribute("estado"));
        model.addAttribute("restricciones", session.getAttribute("restricciones"));
        return "licencia"; // nombre de la plantilla licencia.html
    }

    @PostMapping("/licencia")
    public String procesarFormularioLicencia(@RequestParam Map<String, String> params, HttpSession session) {
        // Guardar en sesión sólo parámetros no vacíos
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String val = entry.getValue();
            if (val != null && !val.isEmpty()) {
                session.setAttribute(entry.getKey(), val);
            }
        }
        // Redirigir al siguiente formulario, por ejemplo, registro vehicular
        return "redirect:/registro-vehicular";
    }
}
