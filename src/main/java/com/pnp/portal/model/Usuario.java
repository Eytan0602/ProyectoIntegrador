package com.pnp.portal.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 8)
    private String dni;

    @Column(nullable = false, length = 150)
    private String nombreCompleto;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(length = 9)
    private String telefono;

    @Column(nullable = false)
    private Boolean activo = true;

    @Column(name = "fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column(name = "ultimo_acceso")
    private LocalDateTime ultimoAcceso;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PermisoLunasPolarizadas> permisos = new ArrayList<>();

    // Constructor vacío
    public Usuario() {
        this.fechaRegistro = LocalDateTime.now();
    }

    // Constructor con parámetros
    public Usuario(String dni, String nombreCompleto, String password, String email) {
        this.dni = dni;
        this.nombreCompleto = nombreCompleto;
        this.password = password;
        this.email = email;
        this.fechaRegistro = LocalDateTime.now();
        this.activo = true;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public LocalDateTime getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(LocalDateTime ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public List<PermisoLunasPolarizadas> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<PermisoLunasPolarizadas> permisos) {
        this.permisos = permisos;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", email='" + email + '\'' +
                ", activo=" + activo +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}