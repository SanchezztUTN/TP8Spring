package com.example.isi.deso.tp_7;


import java.util.Set;

public interface PedidoDAO {
    void crearPedido(Pedido pedido);
    Pedido buscarPedido(long id);
    void actualizarPedido(Pedido pedido);
    void eliminarPedido(long id);
    Set<Pedido> listarPedidos();
}