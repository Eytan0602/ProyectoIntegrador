package com.pnp.portal.controller;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.UUID;

@Controller
public class PagoController {

@GetMapping("/pago")
public String mostrarFormularioPago(@RequestParam(required = false) String metodo,
                                    @RequestParam(required = false) String banco,
                                    HttpSession session,
                                    Model model) {

    String metodoPago = (metodo != null) ? metodo : "tarjeta";

    // Usa el banco para diferenciar el código (si quieres)
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

    String codigoEsperado = (String) session.getAttribute("codigoPago_" + metodo);

    if (codigoIngresado != null && codigoIngresado.equalsIgnoreCase(codigoEsperado)) {
        // Simula datos reales para la sesión
        session.setAttribute("dni", "12345678");
        session.setAttribute("apellidos_nombres", "Juan Pérez");
        session.setAttribute("numero_licencia", "L1234567");
        session.setAttribute("placa", "ABC-123");
        session.setAttribute("compania", "Rimac");
        session.setAttribute("vin", "1HGCM82633A123456");
        session.setAttribute("motor", "MTR456789");
        session.setAttribute("modelo", "Toyota Yaris");
        session.setAttribute("color", "Negro");
        session.setAttribute("fechaFin", "2025-12-31");

        return "redirect:/lunas";
    } else {
        // Mostrar error
        model.addAttribute("metodo", metodo);
        model.addAttribute("banco", banco);
        model.addAttribute("codigoPago", codigoEsperado);
        model.addAttribute("pagoValidado", false);
        model.addAttribute("errorCodigo", "Código inválido. Intente nuevamente.");
String nuevoCodigo = generarCodigoAleatorio();
session.setAttribute("codigoPago_" + metodo, nuevoCodigo);
model.addAttribute("codigoPago", nuevoCodigo);

        return "pago";
    }
}



    @GetMapping("/lunas")
    public void generarPDF(HttpSession session, HttpServletResponse response) throws IOException {
        // Obtener datos desde la sesión
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
