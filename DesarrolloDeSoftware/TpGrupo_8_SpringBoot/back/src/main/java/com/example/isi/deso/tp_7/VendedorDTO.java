package com.example.isi.deso.tp_7;

public class VendedorDTO {
    private Long id;
    private String nombre;
    private String direccion;
    private Coordenada coordenada;

    public VendedorDTO(Vendedor vendedor) {
        this.id = vendedor.getId();
        this.nombre = vendedor.getNombre();
        this.direccion = vendedor.getDireccion();
        this.coordenada = vendedor.getCoordenada();
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Coordenada getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Coordenada coordenada) {
        this.coordenada = coordenada;
    }
}
