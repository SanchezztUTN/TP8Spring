package isi.deso.tp.grupo8;

public interface PagoDAO {
    void crearPago(Pago pago);
    Pago buscarPago(long id);
}