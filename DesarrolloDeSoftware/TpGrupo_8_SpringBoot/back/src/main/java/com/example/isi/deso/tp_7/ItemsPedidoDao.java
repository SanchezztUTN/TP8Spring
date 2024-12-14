package com.example.isi.deso.tp_7;


import java.util.Set;

public interface ItemsPedidoDao {
    Set<ItemPedido> filtrado(char filtro) throws ItemNoEncontradoException;
    Set<ItemPedido> ordenarPorCriterio();
    Set<ItemPedido> busquedaPorRangodePecios(double min, double max) throws ItemNoEncontradoException ;
    Set<ItemPedido> buscarPorRestaurante(Vendedor v) throws ItemNoEncontradoException;
}
