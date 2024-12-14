/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package isi.deso.tp.grupo8;

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
