package com.pnp.portal.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class RegistroVehicularController {

    @GetMapping("/registro-vehicular")
    public String mostrarFormularioRegistroVehicular(HttpSession session, Model model) {
        model.addAttribute("placa", session.getAttribute("placa"));
        model.addAttribute("marca", session.getAttribute("marca"));
        model.addAttribute("modelo", session.getAttribute("modelo"));
        model.addAttribute("anio", session.getAttribute("anio"));
        model.addAttribute("motor", session.getAttribute("motor"));
        model.addAttribute("serie", session.getAttribute("serie"));
        model.addAttribute("vin", session.getAttribute("vin"));
        return "registro-vehicular";  // nombre de la plantilla Thymeleaf
    }

    @PostMapping("/registro-vehicular")
    public String procesarFormularioRegistroVehicular(@RequestParam Map<String, String> params, HttpSession session) {
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String val = entry.getValue();
            if (val != null && !val.isEmpty()) {
                session.setAttribute(entry.getKey(), val);
            }
        }
        // Aquí rediriges al siguiente paso, por ejemplo, soat.jsp o su controlador equivalente
        return "redirect:/soat";
    }
}
