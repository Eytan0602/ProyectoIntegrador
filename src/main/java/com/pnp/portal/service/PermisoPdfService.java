package com.pnp.portal.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.pnp.portal.model.PermisoLunasPolarizadas;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PermisoPdfService {

    public byte[] generarPdfPermiso(PermisoLunasPolarizadas permiso) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            Font tituloFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            Font textoFont = new Font(Font.HELVETICA, 12);

            Paragraph titulo = new Paragraph("Permiso de Lunas Polarizadas", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            document.add(new Paragraph(" "));
            document.add(new Paragraph("DNI: " + permiso.getDni(), textoFont));
            document.add(new Paragraph("Nombre: " + permiso.getNombreCompleto(), textoFont));
            document.add(new Paragraph("Placa: " + permiso.getPlacaVehiculo(), textoFont));
            document.add(new Paragraph("Estado: " + permiso.getEstado(), textoFont));
            document.add(new Paragraph("Fecha Resolución: " + permiso.getFechaResolucion(), textoFont));

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }
}
