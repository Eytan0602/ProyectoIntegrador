package com.pnp.portal.model;

public class Noticia {
    private String titulo;
    private String descripcion;
    private String imagen;
    private String enlace;

    public Noticia(String titulo, String descripcion, String imagen, String enlace) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.enlace = enlace;
    }

    // getters y setters
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getImagen() { return imagen; }
    public String getEnlace() { return enlace; }
}
