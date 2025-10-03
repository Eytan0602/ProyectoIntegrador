package com.pnp.portal.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "soat")
public class Soat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 9, unique = true)
    private String placa;

    @Column(nullable = false, length = 100)
    private String compania;

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(nullable = false, length = 20, unique = true)
    private String numPoliza;

    @Column(nullable = false, length = 4)
    private String codigoSbs;

    // Relación con permiso (un SOAT puede estar asociado a un permiso)
    @OneToOne(mappedBy = "soat", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private PermisoLunasPolarizadas permiso;

    public Soat() {}

    public Soat(String placa, String compania, LocalDate fechaInicio,
                LocalDate fechaFin, String estado, String numPoliza, String codigoSbs) {
        this.placa = placa;
        this.compania = compania;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.numPoliza = numPoliza;
        this.codigoSbs = codigoSbs;
    }

    // ===== Métodos de validación y lógica =====
    public boolean codigoSbsValido() {
        return codigoSbs != null && codigoSbs.matches("\\d{4}");
    }

    public boolean fechasValidas() {
        if (fechaInicio == null || fechaFin == null) return false;
        return fechaFin.isAfter(fechaInicio);
    }

    public boolean estaVigente() {
        if (fechaFin == null) return false;
        LocalDate hoy = LocalDate.now();
        return !fechaFin.isBefore(hoy);
    }

    public long diasRestantes() {
        if (fechaFin == null) return 0;
        LocalDate hoy = LocalDate.now();
        return ChronoUnit.DAYS.between(hoy, fechaFin);
    }

    public void actualizarEstadoSegunFecha() {
        long dias = diasRestantes();
        if (dias < 0) {
            this.estado = "VENCIDO";
        } else if (dias <= 30) {
            this.estado = "POR VENCER";
        } else {
            this.estado = "VIGENTE";
        }
    }

    public boolean esValido() {
        return placa != null && !placa.isEmpty() &&
                compania != null && !compania.isEmpty() &&
                numPoliza != null && !numPoliza.isEmpty() &&
                codigoSbsValido() &&
                fechasValidas() &&
                estado != null && !estado.isEmpty();
    }

    // ===== Getters y Setters =====
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCompania() {
        return compania;
    }

    public void setCompania(String compania) {
        this.compania = compania;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNumPoliza() {
        return numPoliza;
    }

    public void setNumPoliza(String numPoliza) {
        this.numPoliza = numPoliza;
    }

    public String getCodigoSbs() {
        return codigoSbs;
    }

    public void setCodigoSbs(String codigoSbs) {
        this.codigoSbs = codigoSbs;
    }

    public PermisoLunasPolarizadas getPermiso() {
        return permiso;
    }

    public void setPermiso(PermisoLunasPolarizadas permiso) {
        this.permiso = permiso;
    }
}
