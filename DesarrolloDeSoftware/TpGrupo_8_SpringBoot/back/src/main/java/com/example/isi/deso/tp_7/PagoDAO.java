package com.example.isi.deso.tp_7;


public interface PagoDAO {
    void crearPago(Pago pago);
    Pago buscarPago(long id);
}