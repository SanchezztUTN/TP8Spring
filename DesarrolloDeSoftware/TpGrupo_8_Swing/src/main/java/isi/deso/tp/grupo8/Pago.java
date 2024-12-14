package isi.deso.tp.grupo8;

import java.time.LocalDate;

public abstract class Pago {
    private long id;
    private double monto;
    private LocalDate fecha;

    public Pago(long id, double monto, LocalDate fecha) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
    }

    // Métodos getters y setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public abstract String getTipoPago();
}

