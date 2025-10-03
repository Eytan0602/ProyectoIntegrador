package com.pnp.portal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "vehiculos")
public class Vehiculo {

    @Id
    @Column(nullable = false, length = 9, unique = true) // Ej: ABC-123 o ABC1234
    private String placa;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    private Integer anio;

    @Column(length = 20)
    private String motor;

    @Column(length = 30)
    private String serie;

    @Column(length = 17, unique = true)
    private String vin;

    @Column(length = 255)
    private String fotoVerificacion; // Puedes guardar URL o path al archivo

    // Constructor vacío
    public Vehiculo() {}

    // Constructor completo
    public Vehiculo(String placa, String marca, String modelo, Integer anio,
                    String motor, String serie, String vin, String fotoVerificacion) {
        this.placa = placa;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.motor = motor;
        this.serie = serie;
        this.vin = vin;
        this.fotoVerificacion = fotoVerificacion;
    }

    // Getters y Setters
    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }

    public String getMotor() { return motor; }
    public void setMotor(String motor) { this.motor = motor; }

    public String getSerie() { return serie; }
    public void setSerie(String serie) { this.serie = serie; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getFotoVerificacion() { return fotoVerificacion; }
    public void setFotoVerificacion(String fotoVerificacion) { this.fotoVerificacion = fotoVerificacion; }

    // Validaciones
    public boolean vinValido() {
        return vin != null && vin.length() == 17;
    }

    public boolean anioValido() {
        if (anio == null) return false;
        int currentYear = java.time.Year.now().getValue();
        return anio >= 1900 && anio <= currentYear + 1;
    }

    public boolean placaValida() {
        if (placa == null || placa.isEmpty()) return false;
        return placa.matches("^[A-Z]{3}-?\\d{3,4}$");
    }

    // toString
    @Override
    public String toString() {
        return "Vehiculo{" +
                "placa='" + placa + '\'' +
                ", marca='" + marca + '\'' +
                ", modelo='" + modelo + '\'' +
                ", anio=" + anio +
                ", motor='" + motor + '\'' +
                ", serie='" + serie + '\'' +
                ", vin='" + vin + '\'' +
                ", fotoVerificacion='" + fotoVerificacion + '\'' +
                '}';
    }
}
