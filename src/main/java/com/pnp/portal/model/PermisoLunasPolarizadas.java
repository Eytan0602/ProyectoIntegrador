package com.pnp.portal.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "permisos_lunas_polarizadas")
public class PermisoLunasPolarizadas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dni", nullable = false, length = 8)
    private String dni;

    @Column(name = "nombre_completo", nullable = false, length = 150)
    private String nombreCompleto;

    @Column(name = "placa_vehiculo", nullable = false, length = 9)
    private String placaVehiculo;

    @Column(name = "licencia_valida", nullable = false)
    private boolean licenciaValida;

    @Column(name = "soat_vigente", nullable = false)
    private boolean soatVigente;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoTramite estado = EstadoTramite.EN_PROCESO;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "fecha_resolucion")
    private LocalDateTime fechaResolucion;

    @Column(name = "numero_resolucion", length = 200)
    private String numeroResolucion;

    @Column(name = "motivo_denegacion", length = 300)
    private String motivoDenegacion;

    // Relación con Soat
    @OneToOne
    @JoinColumn(name = "soat_id") // FK en tu tabla
    private Soat soat;

    // Campos automáticos de auditoría (opcional, si quieres mapearlos)
    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private LocalDateTime updatedAt;

    public enum EstadoTramite {
        EN_PROCESO,
        ACEPTADA,
        DENEGADA
    }

    // --- Getters y Setters ---
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getDni() { return dni; }

    public void setDni(String dni) { this.dni = dni; }

    public String getNombreCompleto() { return nombreCompleto; }

    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getPlacaVehiculo() { return placaVehiculo; }

    public void setPlacaVehiculo(String placaVehiculo) { this.placaVehiculo = placaVehiculo; }

    public boolean isLicenciaValida() { return licenciaValida; }

    public void setLicenciaValida(boolean licenciaValida) { this.licenciaValida = licenciaValida; }

    public boolean isSoatVigente() { return soatVigente; }

    public void setSoatVigente(boolean soatVigente) { this.soatVigente = soatVigente; }

    public EstadoTramite getEstado() { return estado; }

    public void setEstado(EstadoTramite estado) { this.estado = estado; }

    public LocalDate getFechaSolicitud() { return fechaSolicitud; }

    public void setFechaSolicitud(LocalDate fechaSolicitud) { this.fechaSolicitud = fechaSolicitud; }

    public LocalDateTime getFechaResolucion() { return fechaResolucion; }

    public void setFechaResolucion(LocalDateTime fechaResolucion) { this.fechaResolucion = fechaResolucion; }

    public String getNumeroResolucion() { return numeroResolucion; }

    public void setNumeroResolucion(String numeroResolucion) { this.numeroResolucion = numeroResolucion; }

    public String getMotivoDenegacion() { return motivoDenegacion; }

    public void setMotivoDenegacion(String motivoDenegacion) { this.motivoDenegacion = motivoDenegacion; }
    public Soat getSoat() {
    return soat;
}

public void setSoat(Soat soat) {
    this.soat = soat;
}

}
