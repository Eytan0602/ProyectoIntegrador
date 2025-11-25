package com.pnp.portal.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import com.pnp.portal.model.PermisoLunasPolarizadas;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PermisoPdfService {

    // Colores PNP Oficiales
    private static final Color COLOR_VERDE_PNP = new Color(0, 100, 0);
    private static final Color COLOR_DORADO_PNP = new Color(212, 175, 55);
    private static final Color COLOR_GRIS_OSCURO = new Color(60, 60, 60);
    private static final Color COLOR_TEXTO = new Color(40, 40, 40);

    public byte[] generarPdfPermiso(PermisoLunasPolarizadas permiso) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            
            // Agregar marca de agua y encabezado/pie de página
            writer.setPageEvent(new PdfPageEventHelper() {
                @Override
                public void onEndPage(PdfWriter writer, Document document) {
                    try {
                        // Marca de agua
                        agregarMarcaDeAgua(writer);
                        
                        // Encabezado
                        agregarEncabezado(writer, document);
                        
                        // Pie de página
                        agregarPieDePagina(writer, document);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            document.open();

            // Espaciado superior
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));

            // TÍTULO PRINCIPAL
            Font tituloFont = new Font(Font.HELVETICA, 24, Font.BOLD);
            tituloFont.setColor(COLOR_VERDE_PNP);
            Paragraph titulo = new Paragraph("CERTIFICADO DE AUTORIZACIÓN", tituloFont);
            titulo.setAlignment(Element.ALIGN_CENTER);
            document.add(titulo);

            // Subtítulo
            Font subtituloFont = new Font(Font.HELVETICA, 16, Font.BOLD);
            subtituloFont.setColor(COLOR_DORADO_PNP);
            Paragraph subtitulo = new Paragraph("USO DE LUNAS POLARIZADAS", subtituloFont);
            subtitulo.setAlignment(Element.ALIGN_CENTER);
            subtitulo.setSpacingBefore(5);
            document.add(subtitulo);

            // Número de resolución
            if (permiso.getNumeroResolucion() != null) {
                Font resolucionFont = new Font(Font.HELVETICA, 12, Font.BOLD);
                resolucionFont.setColor(Color.RED);
                Paragraph resolucion = new Paragraph("N° " + permiso.getNumeroResolucion(), resolucionFont);
                resolucion.setAlignment(Element.ALIGN_CENTER);
                resolucion.setSpacingBefore(10);
                resolucion.setSpacingAfter(20);
                document.add(resolucion);
            }

            // Línea divisoria
            PdfPTable lineaTabla = new PdfPTable(1);
            lineaTabla.setWidthPercentage(100);
            lineaTabla.setSpacingBefore(5);
            lineaTabla.setSpacingAfter(5);
            PdfPCell lineaCell = new PdfPCell();
            lineaCell.setBorder(Rectangle.NO_BORDER);
            lineaCell.setBorderWidthBottom(2);
            lineaCell.setBorderColorBottom(COLOR_VERDE_PNP);
            lineaCell.setFixedHeight(2);
            lineaTabla.addCell(lineaCell);
            document.add(lineaTabla);
            document.add(new Paragraph(" "));

            // INFORMACIÓN DEL TITULAR
            Font labelFont = new Font(Font.HELVETICA, 11, Font.BOLD);
            labelFont.setColor(COLOR_GRIS_OSCURO);
            Font valorFont = new Font(Font.HELVETICA, 12, Font.NORMAL);
            valorFont.setColor(COLOR_TEXTO);

            // Tabla de información
            PdfPTable tablaInfo = new PdfPTable(2);
            tablaInfo.setWidthPercentage(90);
            tablaInfo.setWidths(new float[]{1.5f, 3f});
            tablaInfo.setSpacingBefore(20);
            tablaInfo.setSpacingAfter(20);

            // Configurar estilo de celdas
            agregarFilaTabla(tablaInfo, "DNI:", permiso.getDni(), labelFont, valorFont);
            agregarFilaTabla(tablaInfo, "Nombre Completo:", permiso.getNombreCompleto(), labelFont, valorFont);
            agregarFilaTabla(tablaInfo, "Placa del Vehículo:", permiso.getPlacaVehiculo(), labelFont, valorFont);
            agregarFilaTabla(tablaInfo, "Estado:", permiso.getEstado().toString().replace("_", " "), labelFont, valorFont);
            
            if (permiso.getFechaResolucion() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String fechaFormateada;
                
                // Manejar diferentes tipos de fecha
                Object fecha = permiso.getFechaResolucion();
                if (fecha instanceof java.sql.Timestamp) {
                    fechaFormateada = sdf.format(new Date(((java.sql.Timestamp) fecha).getTime()));
                } else if (fecha instanceof java.util.Date) {
                    fechaFormateada = sdf.format((java.util.Date) fecha);
                } else if (fecha instanceof java.time.LocalDateTime) {
                    java.time.LocalDateTime ldt = (java.time.LocalDateTime) fecha;
                    fechaFormateada = ldt.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                } else {
                    fechaFormateada = fecha.toString();
                }
                
                agregarFilaTabla(tablaInfo, "Fecha de Emisión:", fechaFormateada, labelFont, valorFont);
            }

            document.add(tablaInfo);

            // Línea divisoria dorada
            PdfPTable lineaTabla2 = new PdfPTable(1);
            lineaTabla2.setWidthPercentage(100);
            lineaTabla2.setSpacingBefore(5);
            lineaTabla2.setSpacingAfter(5);
            PdfPCell lineaCell2 = new PdfPCell();
            lineaCell2.setBorder(Rectangle.NO_BORDER);
            lineaCell2.setBorderWidthBottom(1);
            lineaCell2.setBorderColorBottom(COLOR_DORADO_PNP);
            lineaCell2.setFixedHeight(1);
            lineaTabla2.addCell(lineaCell2);
            document.add(lineaTabla2);
            document.add(new Paragraph(" "));

            // TEXTOS LEGALES DE CERTIFICACIÓN
            Font textoLegalFont = new Font(Font.HELVETICA, 10, Font.NORMAL);
            textoLegalFont.setColor(COLOR_TEXTO);

            // Párrafo 1
            Paragraph parrafo1 = new Paragraph(
                "La Policía Nacional del Perú, a través de la División de Tránsito y Seguridad Vial, " +
                "CERTIFICA que el vehículo identificado con la placa " + permiso.getPlacaVehiculo() + 
                " se encuentra AUTORIZADO para el uso de lunas polarizadas conforme a lo establecido en " +
                "el Reglamento Nacional de Tránsito y las disposiciones vigentes emitidas por el Ministerio " +
                "de Transportes y Comunicaciones.",
                textoLegalFont
            );
            parrafo1.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo1.setSpacingBefore(15);
            parrafo1.setSpacingAfter(12);
            document.add(parrafo1);

            // Párrafo 2
            Paragraph parrafo2 = new Paragraph(
                "El presente certificado es VÁLIDO ÚNICAMENTE para el vehículo identificado en este documento " +
                "y NO ES TRANSFERIBLE a ningún otro vehículo bajo ninguna circunstancia. La autorización aquí " +
                "conferida es personal e intransferible, quedando el titular como único responsable del uso " +
                "adecuado de las lunas polarizadas conforme a las especificaciones técnicas y de transparencia " +
                "establecidas por ley.",
                textoLegalFont
            );
            parrafo2.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo2.setSpacingAfter(12);
            document.add(parrafo2);

            // Párrafo 3
            Paragraph parrafo3 = new Paragraph(
                "Este certificado NO TIENE FECHA DE VENCIMIENTO y conserva su validez mientras el vehículo " +
                "mantenga la misma placa de identificación y las condiciones técnicas del polarizado cumplan " +
                "con las normas vigentes. El titular deberá portar este documento junto con la tarjeta de " +
                "propiedad vehicular en todo momento. La Policía Nacional del Perú se reserva el derecho de " +
                "verificar en cualquier momento el cumplimiento de las disposiciones aquí establecidas.",
                textoLegalFont
            );
            parrafo3.setAlignment(Element.ALIGN_JUSTIFIED);
            parrafo3.setSpacingAfter(20);
            document.add(parrafo3);

            // Recuadro de advertencia
            PdfPTable tablaAdvertencia = new PdfPTable(1);
            tablaAdvertencia.setWidthPercentage(90);
            tablaAdvertencia.setSpacingBefore(15);
            tablaAdvertencia.setSpacingAfter(25);

            PdfPCell celdaAdvertencia = new PdfPCell();
            celdaAdvertencia.setBorderColor(Color.RED);
            celdaAdvertencia.setBorderWidth(2);
            celdaAdvertencia.setPadding(10);
            celdaAdvertencia.setBackgroundColor(new Color(255, 250, 250));

            Font advertenciaFont = new Font(Font.HELVETICA, 9, Font.BOLD);
            advertenciaFont.setColor(Color.RED);
            Paragraph advertencia = new Paragraph("ADVERTENCIA: ", advertenciaFont);
            
            Font advertenciaTextoFont = new Font(Font.HELVETICA, 8, Font.NORMAL);
            advertenciaTextoFont.setColor(COLOR_GRIS_OSCURO);
            Paragraph advertenciaTexto = new Paragraph(
                "Cualquier alteración, falsificación o uso indebido de este documento constituye delito " +
                "y será sancionado conforme al Código Penal vigente. En caso de pérdida, notificar " +
                "inmediatamente a la Dirección de Tránsito más cercana.",
                advertenciaTextoFont
            );
            
            Phrase fraseCompleta = new Phrase();
            fraseCompleta.add(advertencia);
            fraseCompleta.add(advertenciaTexto);
            celdaAdvertencia.addElement(fraseCompleta);
            
            tablaAdvertencia.addCell(celdaAdvertencia);
            document.add(tablaAdvertencia);

            // FIRMA
            document.add(new Paragraph(" "));
            document.add(new Paragraph(" "));
            
            // Línea de firma
            Paragraph lineaFirma = new Paragraph("_________________________________");
            lineaFirma.setAlignment(Element.ALIGN_CENTER);
            document.add(lineaFirma);

            Font firmaFont = new Font(Font.HELVETICA, 11, Font.BOLD);
            firmaFont.setColor(COLOR_VERDE_PNP);
            Paragraph nombreFirma = new Paragraph("Cmdte. PNP Luis Delgado", firmaFont);
            nombreFirma.setAlignment(Element.ALIGN_CENTER);
            nombreFirma.setSpacingBefore(5);
            document.add(nombreFirma);

            Font cargoFont = new Font(Font.HELVETICA, 9, Font.NORMAL);
            cargoFont.setColor(COLOR_GRIS_OSCURO);
            Paragraph cargo = new Paragraph("Director de Tránsito y Seguridad Vial", cargoFont);
            cargo.setAlignment(Element.ALIGN_CENTER);
            cargo.setSpacingBefore(2);
            document.add(cargo);

            Paragraph institucion = new Paragraph("Policía Nacional del Perú", cargoFont);
            institucion.setAlignment(Element.ALIGN_CENTER);
            institucion.setSpacingBefore(2);
            document.add(institucion);

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }

    private void agregarFilaTabla(PdfPTable tabla, String label, String valor, Font labelFont, Font valorFont) {
        PdfPCell celdaLabel = new PdfPCell(new Phrase(label, labelFont));
        celdaLabel.setBorder(Rectangle.NO_BORDER);
        celdaLabel.setPadding(8);
        celdaLabel.setBackgroundColor(new Color(245, 245, 245));
        
        PdfPCell celdaValor = new PdfPCell(new Phrase(valor, valorFont));
        celdaValor.setBorder(Rectangle.NO_BORDER);
        celdaValor.setPadding(8);
        
        tabla.addCell(celdaLabel);
        tabla.addCell(celdaValor);
    }

    private void agregarMarcaDeAgua(PdfWriter writer) throws DocumentException, IOException {
        PdfContentByte canvas = writer.getDirectContentUnder();
        
        // Guardar estado gráfico
        canvas.saveState();
        
        // Configurar transparencia
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.08f);
        canvas.setGState(gs1);
        
        // Dibujar múltiples escudos como marca de agua
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                float x = 100 + (i * 180);
                float y = 150 + (j * 180);
                dibujarEscudoPNP(canvas, x, y, 80);
            }
        }
        
        // Texto de marca de agua
        PdfGState gs2 = new PdfGState();
        gs2.setFillOpacity(0.05f);
        canvas.setGState(gs2);
        
        canvas.beginText();
        canvas.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED), 60);
        canvas.setColorFill(COLOR_VERDE_PNP);
        canvas.showTextAligned(Element.ALIGN_CENTER, "PNP", 300, 500, 45);
        canvas.showTextAligned(Element.ALIGN_CENTER, "OFICIAL", 300, 300, 45);
        canvas.endText();
        
        canvas.restoreState();
    }

    private void dibujarEscudoPNP(PdfContentByte canvas, float x, float y, float size) {
        canvas.saveState();
        
        // Escudo simplificado de la PNP
        canvas.setColorFill(COLOR_VERDE_PNP);
        
        // Forma de escudo
        canvas.moveTo(x, y + size);
        canvas.lineTo(x - size/2, y + size * 0.8f);
        canvas.lineTo(x - size/2, y + size * 0.3f);
        canvas.curveTo(x - size/2, y, x - size/3, y - size/3, x, y - size * 0.6f);
        canvas.curveTo(x + size/3, y - size/3, x + size/2, y, x + size/2, y + size * 0.3f);
        canvas.lineTo(x + size/2, y + size * 0.8f);
        canvas.closePath();
        canvas.fill();
        
        // Estrella central
        canvas.setColorFill(COLOR_DORADO_PNP);
        dibujarEstrella(canvas, x, y + size/2, size/4);
        
        canvas.restoreState();
    }

    private void dibujarEstrella(PdfContentByte canvas, float x, float y, float radius) {
        float[] xPoints = new float[10];
        float[] yPoints = new float[10];
        
        for (int i = 0; i < 10; i++) {
            double angle = Math.PI / 2 + (2 * Math.PI * i / 10);
            float r = (i % 2 == 0) ? radius : radius / 2.5f;
            xPoints[i] = x + (float)(r * Math.cos(angle));
            yPoints[i] = y + (float)(r * Math.sin(angle));
        }
        
        canvas.moveTo(xPoints[0], yPoints[0]);
        for (int i = 1; i < 10; i++) {
            canvas.lineTo(xPoints[i], yPoints[i]);
        }
        canvas.closePath();
        canvas.fill();
    }

    private void agregarEncabezado(PdfWriter writer, Document document) throws DocumentException, IOException {
        PdfContentByte canvas = writer.getDirectContent();
        
        // Borde superior verde
        canvas.saveState();
        canvas.setColorFill(COLOR_VERDE_PNP);
        canvas.rectangle(0, PageSize.A4.getHeight() - 30, PageSize.A4.getWidth(), 30);
        canvas.fill();
        
        // Borde dorado
        canvas.setColorFill(COLOR_DORADO_PNP);
        canvas.rectangle(0, PageSize.A4.getHeight() - 35, PageSize.A4.getWidth(), 5);
        canvas.fill();
        canvas.restoreState();
        
        // Texto del encabezado
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        canvas.beginText();
        canvas.setFontAndSize(bf, 10);
        canvas.setColorFill(Color.WHITE);
        canvas.showTextAligned(Element.ALIGN_CENTER, "POLICÍA NACIONAL DEL PERÚ - DIVISIÓN DE TRÁNSITO", 
                             PageSize.A4.getWidth() / 2, PageSize.A4.getHeight() - 18, 0);
        canvas.endText();
    }

    private void agregarPieDePagina(PdfWriter writer, Document document) throws DocumentException, IOException {
        PdfContentByte canvas = writer.getDirectContent();
        
        // Borde inferior
        canvas.saveState();
        canvas.setColorFill(COLOR_VERDE_PNP);
        canvas.rectangle(0, 0, PageSize.A4.getWidth(), 30);
        canvas.fill();
        
        canvas.setColorFill(COLOR_DORADO_PNP);
        canvas.rectangle(0, 30, PageSize.A4.getWidth(), 5);
        canvas.fill();
        canvas.restoreState();
        
        // Texto del pie
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
        canvas.beginText();
        canvas.setFontAndSize(bf, 8);
        canvas.setColorFill(Color.WHITE);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fecha = sdf.format(new Date());
        
        canvas.showTextAligned(Element.ALIGN_LEFT, "Documento oficial PNP | Emitido: " + fecha, 40, 12, 0);
        canvas.showTextAligned(Element.ALIGN_RIGHT, "www.pnp.gob.pe | Central: 105", 
                             PageSize.A4.getWidth() - 40, 12, 0);
        canvas.endText();
    }
}