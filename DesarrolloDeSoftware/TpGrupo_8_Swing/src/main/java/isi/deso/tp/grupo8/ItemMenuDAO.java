/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isi.deso.tp.grupo8;

import java.util.Set;

public interface ItemMenuDAO {
    String crearItem(ItemMenu item, Categoria categoria);
    ItemMenu buscarItem(long id);
    void actualizarItem(ItemMenu item, Categoria categoria);
    void eliminarItem(long id);
    Set<ItemMenu> listarItems();
}