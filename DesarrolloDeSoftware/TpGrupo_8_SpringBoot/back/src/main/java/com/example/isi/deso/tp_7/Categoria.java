package com.example.isi.deso.tp_7;

import jakarta.persistence.*;

@Entity
public class Categoria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id_categoria;
    private String descripcion;
    
    @Enumerated(EnumType.STRING) 
    private TipoItem item;

    public Categoria() {
        // Default constructor
    }

    public Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    public Categoria(long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoItem getItem() {
        return item;
    }

    public void setItem(TipoItem item) {
        this.item = item;
    }
}
