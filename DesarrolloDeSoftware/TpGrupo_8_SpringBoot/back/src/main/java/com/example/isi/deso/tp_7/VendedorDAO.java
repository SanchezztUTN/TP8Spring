package com.example.isi.deso.tp_7;


import java.util.Set;

public interface VendedorDAO {
    void crearVendedor(Vendedor vendedor);
    Vendedor buscarVendedor(long id);
    boolean modificarVendedor(Vendedor vendedor);
    void eliminarVendedor(long id);
    Set<Vendedor> listarVendedores();
    void agregarItemMenu(long idVendedor, long idItemMenu);
    Set<ItemMenu> obtenerListaProductos(long idVendedor);
}
