package com.example.isi.deso.tp_7;

import java.util.Set;

public interface ClienteDAO {
    void crearCliente(Cliente cliente);
    Cliente buscarCliente(long id);
    boolean modificarCliente(Cliente cliente);
    void eliminarCliente(long id);
    Set<Cliente> listarClientes();
}