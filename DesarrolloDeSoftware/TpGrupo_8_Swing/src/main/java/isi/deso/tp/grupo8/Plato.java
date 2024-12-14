package isi.deso.tp.grupo8;

public class Plato extends ItemMenu {
    private double calorias;
    private boolean aptoCeliacos;
    private boolean aptoVegetariano;
    private double peso;

    public Plato() {
        this.calorias = 0;
        this.aptoCeliacos = false;
        this.aptoVegetariano = false;
        this.nombre = null;
        this.peso = 0;
    }

    public double getCalorias() {
        return this.calorias;
    }

    public double getPeso() {
        return this.peso;
    }

    public boolean getAptoV() {
        return this.aptoVegetariano;
    }

    public boolean getAptoC() {
        return this.aptoCeliacos;
    }

    public void setCalorias(double calorias) {
        this.calorias = calorias;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setAptoC(boolean aptoCeliacos) {
        this.aptoCeliacos = aptoCeliacos;
    }

    public void setAptoV(boolean aptoVegetariano) {
        this.aptoVegetariano = aptoVegetariano;
    }

    public Plato(double c, boolean aC, boolean aV, String nom, double p, String des, double peso) {
        this.calorias = c;
        this.aptoCeliacos = aC;
        this.aptoVegetariano = aV;
        this.nombre = nom;
        this.precio = p;
        setDesc(des);
        this.peso = peso;
    }

    @Override
    public double peso(double p) {
        this.peso = p + (p * 0.1);
        return this.peso;
    }

    @Override
    public boolean esComida() {
        boolean veg = this.aptoVegano();
        if (veg) {
            System.out.println("Es comida vegana");
        } else {
            System.out.println("Es comida no vegana");
        }
        return true;
    }

    @Override
    public boolean esBebida() {
        return false;
    }

    @Override
    public boolean aptoVegano() {
        return this.aptoVegetariano;
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
