package isi.deso.tp.grupo8;

import java.time.LocalDate;

public class PagoPorMP extends Pago {
    private double recargo;
    private String alias;

    public PagoPorMP(long id, double monto, LocalDate fecha, String alias, double recargo) {
        super(id, monto, fecha);
        this.alias = alias;
        this.recargo = recargo;
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
