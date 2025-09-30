package com.pnp.portal.model;

import java.time.LocalDate;

public class Soat {
    private String placa;
    private String compania;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private String numPoliza;
    private String codigoSbs;

    // Getters y setters

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getCompania() { return compania; }
    public void setCompania(String compania) { this.compania = compania; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public String getNumPoliza() { return numPoliza; }
    public void setNumPoliza(String numPoliza) { this.numPoliza = numPoliza; }

    public String getCodigoSbs() { return codigoSbs; }
    public void setCodigoSbs(String codigoSbs) { this.codigoSbs = codigoSbs; }
}
