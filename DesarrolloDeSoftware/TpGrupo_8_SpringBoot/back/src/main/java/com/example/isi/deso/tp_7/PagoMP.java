package com.example.isi.deso.tp_7;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MP")
public class PagoMP extends Pago {
    private String alias;
    private double recargo;

    public PagoMP() {
    }

    public PagoMP(double monto, String alias) {
        super(monto);
        this.alias = alias;
        this.recargo = 0.4;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public double getRecargo() {
        return recargo;
    }

    public void setRecargo(double recargo) {
        this.recargo = recargo;
    }

    @Override
    public String getTipoPago() {
        return "Mercado Pago";
    }
}

