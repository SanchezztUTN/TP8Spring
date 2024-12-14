package isi.deso.tp.grupo8;

import java.util.HashSet;
import java.util.Set;

public class VendedorController {
    private final VendedorDAO vendedorDAO;
    private final ItemMenuDAO itemMenuDAO;
        private int contadorID = 1; // Para generar IDs únicos

    public VendedorController(VendedorDAO vendedorDAO, ItemMenuDAO itemMenuDAO) {
        this.vendedorDAO = vendedorDAO;
        this.itemMenuDAO = itemMenuDAO;
        

    }
 private long generarIdVendedor() {
        return contadorID++; // Ejemplo: VEN-001
    }

public void crearNuevoVendedor(String nombre, String direccion, Coordenada coordenadas) {
    if (coordenadas.getId() <= 0) {
        throw new IllegalArgumentException("La coordenada debe tener un ID válido antes de crear el vendedor.");
    }
    long id = generarIdVendedor(); // Genera el ID automáticamente para el vendedor
    Vendedor vendedor = new Vendedor(id, nombre, direccion, coordenadas, new HashSet<>());
    vendedorDAO.crearVendedor(vendedor); // Guarda el vendedor en la base de datos
}



    public Vendedor buscarVendedor(long id) {
        return vendedorDAO.buscarVendedor(id);
    }

    public boolean modificarVendedor(Vendedor vendedor) {
        return vendedorDAO.modificarVendedor(vendedor);
    }

    public void eliminarVendedor(long id) {
        vendedorDAO.eliminarVendedor(id);
    }

    public Set<Vendedor> obtenerListaVendedores() {
        return vendedorDAO.listarVendedores();
    }

    public void agregarItemMenuAVendedor(long idVendedor, long idItemMenu) {
        vendedorDAO.agregarItemMenu(idVendedor, idItemMenu);
    }

    public Set<ItemMenu> obtenerListaProductosVendedor(long idVendedor) {
        return vendedorDAO.obtenerListaProductos(idVendedor);
    }
}


