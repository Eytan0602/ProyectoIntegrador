package com.pnp.portal.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "solicitudes_dni")
public class SolicitudDni {

    @Id
    @Column(nullable = false, length = 8, unique = true)
    private String dni;

    @Column(nullable = false, length = 50)
    private String apellido_paterno;

    @Column(nullable = false, length = 50)
    private String apellido_materno;

    @Column(nullable = false, length = 100)
    private String nombres;

    @Column(nullable = false)
    private LocalDate fecha_nacimiento;

    @Column(length = 10)
    private String genero;

    @Column(length = 200)
    private String direccion;

    @Column(length = 6)
    private String ubigeo;

    @Column(length = 50)
    private String distrito;

    @Column(length = 50)
    private String provincia;

    @Column(length = 50)
    private String departamento;

    @Transient // No lo guardamos en BD (solo para validaciones de UI/API)
    private String captcha;

    // Constructor vacío
    public SolicitudDni() {}

    // Constructor completo
    public SolicitudDni(String dni, String apellido_paterno, String apellido_materno,
                        String nombres, LocalDate fecha_nacimiento, String genero,
                        String direccion, String ubigeo, String distrito,
                        String provincia, String departamento) {
        this.dni = dni;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.nombres = nombres;
        this.fecha_nacimiento = fecha_nacimiento;
        this.genero = genero;
        this.direccion = direccion;
        this.ubigeo = ubigeo;
        this.distrito = distrito;
        this.provincia = provincia;
        this.departamento = departamento;
    }

    // Getters y Setters
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }

    public String getApellido_paterno() { return apellido_paterno; }
    public void setApellido_paterno(String apellido_paterno) { this.apellido_paterno = apellido_paterno; }

    public String getApellido_materno() { return apellido_materno; }
    public void setApellido_materno(String apellido_materno) { this.apellido_materno = apellido_materno; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public LocalDate getFecha_nacimiento() { return fecha_nacimiento; }
    public void setFecha_nacimiento(LocalDate fecha_nacimiento) { this.fecha_nacimiento = fecha_nacimiento; }

    public String getGenero() { return genero; }
    public void setGenero(String genero) { this.genero = genero; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getUbigeo() { return ubigeo; }
    public void setUbigeo(String ubigeo) { this.ubigeo = ubigeo; }

    public String getDistrito() { return distrito; }
    public void setDistrito(String distrito) { this.distrito = distrito; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getCaptcha() { return captcha; }
    public void setCaptcha(String captcha) { this.captcha = captcha; }

    // Helper
    public String getNombreCompleto() {
        return apellido_paterno + " " + apellido_materno + " " + nombres;
    }

    @Override
    public String toString() {
        return "SolicitudDni{" +
                "dni='" + dni + '\'' +
                ", apellido_paterno='" + apellido_paterno + '\'' +
                ", apellido_materno='" + apellido_materno + '\'' +
                ", nombres='" + nombres + '\'' +
                ", fecha_nacimiento=" + fecha_nacimiento +
                ", genero='" + genero + '\'' +
                ", direccion='" + direccion + '\'' +
                ", ubigeo='" + ubigeo + '\'' +
                ", distrito='" + distrito + '\'' +
                ", provincia='" + provincia + '\'' +
                ", departamento='" + departamento + '\'' +
                '}';
    }
}
