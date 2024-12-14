package isi.deso.tp.grupo8;

import java.util.Set;

public class ItemsMenuController {
    private final ItemMenuDAO itemMenuDAO;

    public ItemsMenuController(ItemMenuDAO itemMenuDAO) {
        this.itemMenuDAO = itemMenuDAO;
    }

    public String crearPlato(String nombre, String descripcion, double precio, double calorias, boolean aptoCeliaco, boolean aptoVegetariano, double peso, Categoria categoria) {
        Plato plato = new Plato(calorias, aptoCeliaco, aptoVegetariano, nombre, precio, descripcion, peso);
        return itemMenuDAO.crearItem(plato, categoria);
    }

    public String crearBebida(String nombre, String descripcion, double precio, double volumen, double graduacionAlcoholica, Categoria categoria) {
        Bebida bebida = new Bebida(graduacionAlcoholica, volumen, nombre, precio, descripcion);
        return itemMenuDAO.crearItem(bebida, categoria);
    }

    public ItemMenu buscarItem(long id) {
        return itemMenuDAO.buscarItem(id);
    }

    public void modificarItem(long id, String nombre, String descripcion, double precio, Categoria categoria) {
        ItemMenu item = buscarItem(id);
        if (item != null) {
            item.setNombre(nombre);
            item.setDesc(descripcion);
            item.setPrecio(precio);
            itemMenuDAO.actualizarItem(item, categoria);
        }
    }

    public void eliminarItem(long id) {
        itemMenuDAO.eliminarItem(id);
    }

    public Set<ItemMenu> obtenerListaItems() {
        return itemMenuDAO.listarItems();
    }

    
}
