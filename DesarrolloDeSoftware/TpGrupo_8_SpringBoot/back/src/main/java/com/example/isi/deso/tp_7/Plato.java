package com.example.isi.deso.tp_7;

import jakarta.persistence.Entity;

@Entity
public class Plato extends ItemMenu {
    private double calorias;
    private double peso;
    private boolean aptoCeliacos;
    private boolean aptoVegetarianos;

    public Plato() {
    }

    public Plato(double calorias, double peso, boolean aptoCeliacos, boolean aptoVegetarianos, String nombre, double precio) {
        this.calorias = calorias;
        this.peso = peso;
        this.aptoCeliacos = aptoCeliacos;
        this.aptoVegetarianos = aptoVegetarianos;
        this.nombre = nombre;
        this.precio = precio;
    }

    public double getCalorias() {
        return calorias;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public boolean isAptoCeliacos() {
        return aptoCeliacos;
    }

    public void setAptoCeliacos(boolean aptoCeliacos) {
        this.aptoCeliacos = aptoCeliacos;
    }

    public boolean isAptoVegetarianos() {
        return aptoVegetarianos;
    }

    public void setAptoVegetarianos(boolean aptoVegetarianos) {
        this.aptoVegetarianos = aptoVegetarianos;
    }

    @Override
    public double peso(double p) {
        return p;
    }

    @Override
    public boolean esComida() {
        return true;
    }

    @Override
    public boolean esBebida() {
        return false;
    }

    @Override
    public boolean aptoVegano() {
        return this.aptoVegetarianos;
    }

    @Override
    public boolean esAlcoholica() {
        return false;
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