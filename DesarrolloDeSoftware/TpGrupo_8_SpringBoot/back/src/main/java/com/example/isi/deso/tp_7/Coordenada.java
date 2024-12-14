package com.example.isi.deso.tp_7;

import jakarta.persistence.*;

@Entity
public class Coordenada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double latitud;
    private double longitud;

    public Coordenada() {
        // Default constructor
    }

    public Coordenada(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public Coordenada(double latitud, double longitud, long id) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.id = id;
    }

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
}
