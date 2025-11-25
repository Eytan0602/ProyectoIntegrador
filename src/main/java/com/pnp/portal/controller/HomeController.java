package com.pnp.portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("titulo", "Policía Nacional del Perú - Portal de Servicios");
        return "index";
    }
}