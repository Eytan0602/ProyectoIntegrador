package com.pnp.portal.controller;

import com.pnp.portal.model.Soat;
import com.pnp.portal.repository.SoatRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/soat")
public class SoatController {
    
    @Autowired
    private SoatRepository soatRepository;
    
    @GetMapping
    public String mostrarFormularioSoat(HttpSession session, Model model) {
        Soat soat = new Soat();
        
        String placa = (String) session.getAttribute("placa");
        if (placa != null) {
            soat.setPlaca(placa);
        }
        
        model.addAttribute("soat", soat);
        return "soat";
    }
    
    @PostMapping
    public String procesarFormularioSoat(@ModelAttribute Soat soat, 
                                          HttpSession session,
                                          RedirectAttributes redirectAttributes) {
        
        // GUARDAR EN BASE DE DATOS
        try {
            soatRepository.save(soat);
            System.out.println(" SOAT GUARDADO: " + soat.getNumPoliza());
        } catch (Exception e) {
            System.err.println("ERROR AL GUARDAR SOAT: " + e.getMessage());
            e.printStackTrace();
        }

        session.setAttribute("numPoliza", soat.getNumPoliza());
        session.setAttribute("companiaSeguro", soat.getCompania());
        session.setAttribute("soatId", soat.getId());
        
        redirectAttributes.addFlashAttribute("soat", soat);
        return "redirect:/pago";
    }
}