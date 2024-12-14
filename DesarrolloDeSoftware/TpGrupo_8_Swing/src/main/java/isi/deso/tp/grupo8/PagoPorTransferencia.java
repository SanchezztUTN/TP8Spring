package isi.deso.tp.grupo8;

import java.time.LocalDate;

public class PagoPorTransferencia extends Pago {
    private String cbu;
    private String cuit;
    private double recargo;

    public PagoPorTransferencia(long id, double monto, LocalDate fecha, String cbu, String cuit, double recargo) {
        super(id, monto, fecha);
        this.cbu = cbu;
        this.cuit = cuit;
        this.recargo = recargo; // Initialize recargo
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
   public void setRecargo(double recargo) {
        this.recargo = recargo;
    }
    public double getRecargo() {
        return recargo;
    }
    @Override
    public String getTipoPago() {
        return "Transferencia";
    }
}
