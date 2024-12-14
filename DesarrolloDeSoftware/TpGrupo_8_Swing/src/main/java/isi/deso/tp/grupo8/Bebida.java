package isi.deso.tp.grupo8;

public class Bebida extends ItemMenu{

    private double volumen;
    private double graduacionAlcoholica;

    public Bebida(double alc, double tam,String nom, double p, String desc){
        this.graduacionAlcoholica = alc;
        this.volumen = tam;
        this.nombre = nom;
        this.precio = p;
        setDesc(desc);
    }
public double getVol(){
    return this.volumen;
}
public double getGradA(){
    return this.graduacionAlcoholica;
}
    @Override
    public double peso(double p) {    
        double peso;          
        if (this.graduacionAlcoholica !=  0){
        peso = (p*0.99) + p*0.2;
        }else{
        peso = (p*1.04) + p*0.2;
        }
    return peso;
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

    public boolean esAlcoholica(){
        boolean k;
        if(this.graduacionAlcoholica != 0){
            k = true;
        }else{
            k = false;
        }
        return k;
    }

    @Override
    public String toString() {
        return this.nombre;
    }
    
    @Override
    public double getPrecio(){
        return this.precio;
    }
}
