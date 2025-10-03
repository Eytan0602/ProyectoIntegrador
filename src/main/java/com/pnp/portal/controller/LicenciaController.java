package com.pnp.portal.controller;

import com.pnp.portal.model.Licencia;
import com.pnp.portal.repository.LicenciaRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class LicenciaController {

    @Autowired
    private LicenciaRepository licenciaRepository;

    @GetMapping("/licencia")
    public String mostrarFormularioLicencia(HttpSession session, Model model) {
        Licencia licencia = (Licencia) session.getAttribute("licencia");
        
        if (licencia == null) {
            licencia = new Licencia();
            String dni = (String) session.getAttribute("dni");
            if (dni != null) {
                licencia.setDni(dni);
            }
        }
        
        model.addAttribute("licencia", licencia);
        model.addAttribute("dni", licencia.getDni());
        model.addAttribute("nombre_completo", licencia.getNombreCompleto());
        model.addAttribute("numero_licencia", licencia.getNumeroLicencia());
        model.addAttribute("categoria", licencia.getCategoria());
        model.addAttribute("fecha_expedicion", licencia.getFechaExpedicion());
        model.addAttribute("fecha_vencimiento", licencia.getFechaVencimiento());
        model.addAttribute("estado", licencia.getEstado() != null ? licencia.getEstado() : "ACTIVA");
        model.addAttribute("restricciones", licencia.getRestricciones());
        
        return "licencia";
    }

    @PostMapping("/licencia")
    public String procesarFormularioLicencia(
            @RequestParam("dni") String dni,
            @RequestParam("nombre_completo") String nombreCompleto,
            @RequestParam("numero_licencia") String numeroLicencia,
            @RequestParam("categoria") String categoria,
            @RequestParam("fecha_expedicion") String fechaExpedicion,
            @RequestParam("fecha_vencimiento") String fechaVencimiento,
            @RequestParam(value = "estado", required = false, defaultValue = "ACTIVA") String estado,
            @RequestParam(value = "restricciones", required = false) String restricciones,
            HttpSession session) {
        
        Licencia licencia = new Licencia();
        licencia.setDni(dni);
        licencia.setNombreCompleto(nombreCompleto);
        licencia.setNumeroLicencia(numeroLicencia);
        licencia.setCategoria(categoria);
        licencia.setFechaExpedicion(LocalDate.parse(fechaExpedicion));
        licencia.setFechaVencimiento(LocalDate.parse(fechaVencimiento));
        licencia.setEstado(estado);
        licencia.setRestricciones(restricciones);
        
        // GUARDAR EN BASE DE DATOS
        try {
            licenciaRepository.save(licencia);
            System.out.println("✅ LICENCIA GUARDADA: " + licencia.getNumeroLicencia());
        } catch (Exception e) {
            System.err.println("❌ ERROR AL GUARDAR LICENCIA: " + e.getMessage());
            e.printStackTrace();
        }

        session.setAttribute("licencia", licencia);
        session.setAttribute("dni", dni);
        
        return "redirect:/registro-vehicular";
    }
}
