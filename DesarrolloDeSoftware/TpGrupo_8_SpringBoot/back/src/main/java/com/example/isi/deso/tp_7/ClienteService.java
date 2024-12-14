package com.example.isi.deso.tp_7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CoordenadaDAO coordenadaDAO;

    public Cliente crearCliente(Cliente cliente) {
        // Persist the Coordenada entity first
        Coordenada coordenada = cliente.getCoor();
        if (coordenada != null) {
            coordenada = coordenadaDAO.save(coordenada);
            cliente.setCoor(coordenada);
        }
        return clienteRepository.save(cliente);
    }

    public Optional<Cliente> buscarCliente(long id) {
        return clienteRepository.findById(id);
    }

    public Cliente modificarCliente(Cliente cliente) {
        Coordenada coordenada = cliente.getCoor();
        if (coordenada != null) {
            coordenada = coordenadaDAO.save(coordenada);
            cliente.setCoor(coordenada);
        }
        return clienteRepository.save(cliente);
    }

    public void eliminarCliente(long id) {
        clienteRepository.deleteById(id);
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
}
