package com.pnp.portal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "licencias")
public class Licencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // BIGSERIAL en PostgreSQL
    private Long id;

    @Column(nullable = false, length = 8)
    private String dni;

    @Column(nullable = false, length = 150)
    private String nombreCompleto;

    @Column(nullable = false, unique = true, length = 20)
    private String numeroLicencia;

    @Column(nullable = false, length = 10)
    private String categoria;

    @Column(nullable = false)
    private LocalDate fechaExpedicion;

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(length = 200)
    private String restricciones;

    // Constructor vacío
    public Licencia() {}

    // Constructor con parámetros
    public Licencia(String dni, String nombreCompleto, String numeroLicencia,
                    String categoria, LocalDate fechaExpedicion,
                    LocalDate fechaVencimiento, String estado, String restricciones) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.numeroLicencia = numeroLicencia;
        this.categoria = categoria;
        this.fechaExpedicion = fechaExpedicion;
        this.fechaVencimiento = fechaVencimiento;
        this.estado = estado;
        this.restricciones = restricciones;
    }

    // Getters y Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getDni() { return dni; }

    public void setDni(String dni) { this.dni = dni; }

    public String getNombreCompleto() { return nombreCompleto; }

    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getNumeroLicencia() { return numeroLicencia; }

    public void setNumeroLicencia(String numeroLicencia) { this.numeroLicencia = numeroLicencia; }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }

    public LocalDate getFechaExpedicion() { return fechaExpedicion; }

    public void setFechaExpedicion(LocalDate fechaExpedicion) { this.fechaExpedicion = fechaExpedicion; }

    public LocalDate getFechaVencimiento() { return fechaVencimiento; }

    public void setFechaVencimiento(LocalDate fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public String getEstado() { return estado; }

    public void setEstado(String estado) { this.estado = estado; }

    public String getRestricciones() { return restricciones; }

    public void setRestricciones(String restricciones) { this.restricciones = restricciones; }

    // Métodos de validación
    public boolean estaVencida() {
        return fechaVencimiento != null && fechaVencimiento.isBefore(LocalDate.now());
    }

    public boolean esValida() {
        return "ACTIVA".equalsIgnoreCase(estado) && !estaVencida();
    }

    @Override
    public String toString() {
        return "Licencia{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", numeroLicencia='" + numeroLicencia + '\'' +
                ", categoria='" + categoria + '\'' +
                ", fechaExpedicion=" + fechaExpedicion +
                ", fechaVencimiento=" + fechaVencimiento +
                ", estado='" + estado + '\'' +
                ", restricciones='" + restricciones + '\'' +
                '}';
    }
}
