package isi.deso.tp.grupo8;

import java.util.Set;

public class ClienteController {
    private final ClienteDAO clienteMemory;
    private int contadorID = 1; // Para generar IDs únicos

    public ClienteController(ClienteDAO clienteMemory) {
        this.clienteMemory = clienteMemory;
    }

    private long generarIdCliente() {
        return contadorID++; // Genera un ID único para el cliente
    }

    public void crearNuevoCliente(String cuit, String email, String direccion, Coordenada coordenadas, String alias, String cbu) {
        if (coordenadas.getId() <= 0) {
            throw new IllegalArgumentException("La coordenada debe tener un ID válido antes de crear el cliente.");
        }
        long id = generarIdCliente(); // Genera el ID automáticamente para el cliente
        Cliente cliente = new Cliente(id, cuit, email, direccion, coordenadas, alias, cbu);
        clienteMemory.crearCliente(cliente); // Guarda el cliente en la base de datos
    }

    public Cliente buscarCliente(long id) {
        return clienteMemory.buscarCliente(id);
    }

    public boolean modificarCliente(long id, String cuit, String email, String direccion, Coordenada coordenadas, String alias, String cbu) {
        Cliente cliente = new Cliente(id, cuit, email, direccion, coordenadas, alias, cbu);
        return clienteMemory.modificarCliente(cliente);
    }

    public void eliminarCliente(long id) {
        clienteMemory.eliminarCliente(id);
    }

    public Set<Cliente> obtenerListaClientes() {
        return clienteMemory.listarClientes();
    }
}
