package com.pnp.portal.controller;

import com.pnp.portal.model.Soat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
@RequestMapping("/soat")
public class SoatController {

    @GetMapping
    public String mostrarFormularioSoat(Model model) {
        model.addAttribute("soat", new Soat());
        return "soat"; // <-- este nombre debe coincidir con el archivo soat.html
    }

    @PostMapping
    public String procesarFormularioSoat(@ModelAttribute Soat soat, RedirectAttributes redirectAttributes) {
        // lógica para guardar o procesar

        // opcional: pasar datos a la siguiente vista
        redirectAttributes.addFlashAttribute("soat", soat);

        return "redirect:/pago"; // siguiente vista
    }
}

