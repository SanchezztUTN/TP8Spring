package com.example.isi.deso.tp_7;

import jakarta.persistence.Entity;

@Entity
public class Bebida extends ItemMenu {
    private double volumen;
    private double graduacionAlcoholica;

    public Bebida() {
    }

    public Bebida(double graduacionAlcoholica, double volumen, String nombre, double precio) {
        this.graduacionAlcoholica = graduacionAlcoholica;
        this.volumen = volumen;
        this.nombre = nombre;
        this.precio = precio;
    }

    public double getVolumen() {
        return volumen;
    }

    public void setVolumen(double volumen) {
        this.volumen = volumen;
    }

    public double getGraduacionAlcoholica() {
        return graduacionAlcoholica;
    }

    public void setGraduacionAlcoholica(double graduacionAlcoholica) {
        this.graduacionAlcoholica = graduacionAlcoholica;
    }

    @Override
    public double peso(double p) {
        return p;
    }

    @Override
    public boolean esComida() {
        return false;
    }

    @Override
    public boolean esBebida() {
        return true;
    }

    @Override
    public boolean aptoVegano() {
        return false;
    }

    @Override
    public boolean esAlcoholica() {
        return this.graduacionAlcoholica > 0;
    }

    @Override
    public String toString() {
        return this.nombre;
    }

    @Override
    public double getPrecio() {
        return this.precio;
    }
}