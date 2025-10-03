package com.pnp.portal.controller;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.pnp.portal.model.PermisoLunasPolarizadas;
import com.pnp.portal.model.SolicitudDni;
import com.pnp.portal.model.Soat;
import com.pnp.portal.model.Licencia;
import com.pnp.portal.repository.PermisoRepository;
import com.pnp.portal.repository.SolicitudDniRepository;
import com.pnp.portal.repository.SoatRepository;
import com.pnp.portal.repository.LicenciaRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Controller
public class PagoController {

    @Autowired
    private PermisoRepository permisoRepository;
    
    @Autowired
    private SolicitudDniRepository solicitudDniRepository;
    
    @Autowired
    private SoatRepository soatRepository;
    
    @Autowired
    private LicenciaRepository licenciaRepository;

    @GetMapping("/pago")
    public String mostrarFormularioPago(@RequestParam(required = false) String metodo,
                                        @RequestParam(required = false) String banco,
                                        HttpSession session,
                                        Model model) {

        String metodoPago = (metodo != null) ? metodo : "tarjeta";
        String codigo = generarCodigoAleatorio();
        session.setAttribute("codigoPago_" + metodoPago + "_" + (banco != null ? banco : "ninguno"), codigo);

        model.addAttribute("metodo", metodoPago);
        model.addAttribute("banco", banco);
        model.addAttribute("codigoPago", codigo);
        model.addAttribute("pagoValidado", false);
        model.addAttribute("errorCodigo", null);

        return "pago";
    }

    @PostMapping("/pago")
    public String procesarPago(@RequestParam String metodo,
                               @RequestParam(required = false) String banco,
                               @RequestParam(required = false) String codigoIngresado,
                               HttpSession session,
                               Model model) {

        String bancoKey = (banco != null) ? banco : "ninguno";
        String claveCodigo = "codigoPago_" + metodo + "_" + bancoKey;
        String codigoEsperado = (String) session.getAttribute(claveCodigo);

        if (codigoIngresado != null && codigoIngresado.equalsIgnoreCase(codigoEsperado)) {
            
            // ✅ CREAR REGISTRO DE PERMISO AQUÍ
            try {
                String dni = (String) session.getAttribute("dni");
                String placa = (String) session.getAttribute("placa");
                
                if (dni != null && placa != null) {
                    // Buscar datos relacionados
                    Optional<SolicitudDni> solicitudOpt = solicitudDniRepository.findById(dni);
                    Optional<Soat> soatOpt = soatRepository.findById((Long) session.getAttribute("soatId"));
                    
                    if (solicitudOpt.isPresent()) {
                        SolicitudDni solicitud = solicitudOpt.get();
                        
                        // Crear el permiso
                        PermisoLunasPolarizadas permiso = new PermisoLunasPolarizadas();
                        permiso.setDni(dni);
                        permiso.setNombreCompleto(solicitud.getNombreCompleto());
                        permiso.setPlacaVehiculo(placa);
                        permiso.setLicenciaValida(true); // Validar según lógica
                        permiso.setSoatVigente(soatOpt.isPresent() && soatOpt.get().estaVigente());
                        permiso.setEstado(PermisoLunasPolarizadas.EstadoTramite.EN_PROCESO);
                        permiso.setFechaSolicitud(LocalDate.now());
                        
                        // Asociar SOAT si existe
                        if (soatOpt.isPresent()) {
                            permiso.setSoat(soatOpt.get());
                        }
                        
                        // GUARDAR EN BD
                        permisoRepository.save(permiso);
                        System.out.println("✅ PERMISO CREADO PARA DNI: " + dni);
                    }
                }
            } catch (Exception e) {
                System.err.println("❌ ERROR AL CREAR PERMISO: " + e.getMessage());
                e.printStackTrace();
            }
            
            model.addAttribute("mensaje", "Pago validado correctamente. ¡Gracias!");
            model.addAttribute("pagoValidado", true);
            model.addAttribute("metodo", metodo);
            model.addAttribute("banco", banco);
            return "pago";
            
        } else {
            model.addAttribute("metodo", metodo);
            model.addAttribute("banco", banco);
            String nuevoCodigo = generarCodigoAleatorio();
            session.setAttribute(claveCodigo, nuevoCodigo);
            model.addAttribute("codigoPago", nuevoCodigo);
            model.addAttribute("pagoValidado", false);
            model.addAttribute("errorCodigo", "Código inválido. Intente nuevamente.");
            return "pago";
        }
    }

    @GetMapping("/lunas")
    public void generarPDF(HttpSession session, HttpServletResponse response) throws IOException {
        String dni = (String) session.getAttribute("dni");
        String apellidos = (String) session.getAttribute("apellidos_nombres");
        String placa = (String) session.getAttribute("placa");
        String modelo = (String) session.getAttribute("modelo");
        String color = (String) session.getAttribute("color");

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=permiso-vehicular.pdf");

        try (OutputStream out = response.getOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();
            document.addTitle("Permiso Vehicular");
            document.add(new Paragraph("PERMISO VEHICULAR"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("DNI: " + dni));
            document.add(new Paragraph("Apellidos y Nombres: " + apellidos));
            document.add(new Paragraph("Placa: " + placa));
            document.add(new Paragraph("Modelo: " + modelo));
            document.add(new Paragraph("Color: " + color));
            document.add(new Paragraph("Fecha de Emisión: " + LocalDate.now()));
            document.close();
        }
    }

    private String generarCodigoAleatorio() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}