package com.example.isi.deso.tp_7;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Transferencia")
public class PagoTransferencia extends Pago {
    private String cbu;
    private String cuit;
    private double recargo;

    public PagoTransferencia() {
    }

    public PagoTransferencia(double monto, String cbu, String cuit) {
        super(monto);
        this.cbu = cbu;
        this.cuit = cuit;
        this.recargo = 0.2;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public double getRecargo() {
        return recargo;
    }

    public void setRecargo(double recargo) {
        this.recargo = recargo;
    }

    @Override
    public String getTipoPago() {
        return "Transferencia";
    }
}
