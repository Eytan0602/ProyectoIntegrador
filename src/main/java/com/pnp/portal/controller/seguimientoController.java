package com.pnp.portal.controller;

import com.lowagie.text.DocumentException;
import com.pnp.portal.model.PermisoLunasPolarizadas;
import com.pnp.portal.repository.PermisoRepository;
import com.pnp.portal.service.PermisoPdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

@Controller
@RequestMapping("/seguimiento-tramite")
public class seguimientoController {

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private PermisoPdfService permisoPdfService;

    // Mostrar formulario de consulta
    @GetMapping
    public String mostrarFormularioConsulta(Model model) {
        model.addAttribute("tramiteEncontrado", false);
        return "seguimiento-tramite";
    }

    // Buscar trámite por DNI
    @PostMapping
    public String consultarTramite(@RequestParam String dni, Model model) {
        dni = dni.trim();

        if (dni.isEmpty() || !dni.matches("\\d{8}")) {
            model.addAttribute("error", "Por favor, ingrese un DNI válido de 8 dígitos.");
            model.addAttribute("tramiteEncontrado", false);
            return "seguimiento-tramite";
        }

        Optional<PermisoLunasPolarizadas> permisoOpt = permisoRepository.findByDni(dni);

        if (permisoOpt.isPresent()) {
            PermisoLunasPolarizadas permiso = permisoOpt.get();
            model.addAttribute("tramiteEncontrado", true);
            model.addAttribute("permiso", permiso);
            model.addAttribute("dni", dni);
        } else {
            model.addAttribute("tramiteEncontrado", false);
            model.addAttribute("error", "No se encontró ningún trámite asociado al DNI " + dni);
        }

        return "seguimiento-tramite";
    }

    // Generar y descargar PDF
    @GetMapping("/pdf/{dni}")
    public void descargarPdf(@PathVariable String dni, HttpServletResponse response) 
            throws IOException, DocumentException {

        Optional<PermisoLunasPolarizadas> permisoOpt = permisoRepository.findByDni(dni);

        if (permisoOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trámite no encontrado");
            return;
        }

        PermisoLunasPolarizadas permiso = permisoOpt.get();

        if (permiso.getEstado() != PermisoLunasPolarizadas.EstadoTramite.ACEPTADA) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, 
                "El PDF solo está disponible para trámites ACEPTADOS");
            return;
        }

        byte[] pdfBytes = permisoPdfService.generarPdfPermiso(permiso);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", 
            "attachment; filename=permiso-lunas-polarizadas-" + dni + ".pdf");
        response.setContentLength(pdfBytes.length);

        try (OutputStream out = response.getOutputStream()) {
            out.write(pdfBytes);
            out.flush();
        }
    }
}
