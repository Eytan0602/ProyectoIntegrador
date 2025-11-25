package com.pnp.portal.controller;

import com.lowagie.text.DocumentException;
import com.pnp.portal.model.PermisoLunasPolarizadas;
import com.pnp.portal.model.Usuario;
import com.pnp.portal.repository.PermisoRepository;
import com.pnp.portal.repository.UsuarioRepository;
import com.pnp.portal.service.PermisoPdfService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/seguimiento-tramite")
public class seguimientoController {

    @Autowired
    private PermisoRepository permisoRepository;

    @Autowired
    private PermisoPdfService permisoPdfService;
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Mostrar lista de trámites del usuario autenticado
    @GetMapping
    public String mostrarTramitesUsuario(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            String dni = auth.getName();
            Optional<Usuario> usuarioOpt = usuarioRepository.findByDni(dni);
            
            if (usuarioOpt.isPresent()) {
                Usuario usuario = usuarioOpt.get();
                
                // Obtener todos los trámites del usuario
                List<PermisoLunasPolarizadas> tramites = permisoRepository
                    .findByUsuarioOrderByFechaSolicitudDesc(usuario);
                
                model.addAttribute("tramites", tramites);
                model.addAttribute("usuario", usuario);
                model.addAttribute("tieneTramites", !tramites.isEmpty());
                
                return "mis-tramites";
            }
        }
        
        // Si no está autenticado, mostrar formulario de búsqueda
        model.addAttribute("tramiteEncontrado", false);
        return "seguimiento-tramite";
    }

    // Buscar trámite específico por DNI
    @PostMapping
    public String consultarTramite(@RequestParam String dni, Model model) {
        dni = dni.trim();

        if (dni.isEmpty() || !dni.matches("\\d{8}")) {
            model.addAttribute("error", "Por favor, ingrese un DNI válido de 8 dígitos.");
            model.addAttribute("tramiteEncontrado", false);
            return "seguimiento-tramite";
        }

        // Verificar que el usuario autenticado sea el dueño del trámite
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dniAutenticado = null;
        
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            dniAutenticado = auth.getName();
        }

        Optional<PermisoLunasPolarizadas> permisoOpt = permisoRepository.findByDni(dni);

        if (permisoOpt.isPresent()) {
            PermisoLunasPolarizadas permiso = permisoOpt.get();
            
            // Verificar que el usuario autenticado sea el dueño del trámite
            if (dniAutenticado != null && permiso.getUsuario() != null) {
                if (!permiso.getUsuario().getDni().equals(dniAutenticado)) {
                    model.addAttribute("tramiteEncontrado", false);
                    model.addAttribute("error", "No tiene permisos para ver este trámite");
                    return "seguimiento-tramite";
                }
            }
            
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

        // Verificar autenticación
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String dniAutenticado = null;
        
        if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
            dniAutenticado = auth.getName();
        }

        Optional<PermisoLunasPolarizadas> permisoOpt = permisoRepository.findByDni(dni);

        if (permisoOpt.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Trámite no encontrado");
            return;
        }

        PermisoLunasPolarizadas permiso = permisoOpt.get();
        
        // Verificar que el usuario autenticado sea el dueño del trámite
        if (dniAutenticado != null && permiso.getUsuario() != null) {
            if (!permiso.getUsuario().getDni().equals(dniAutenticado)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tiene permisos para descargar este PDF");
                return;
            }
        }

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