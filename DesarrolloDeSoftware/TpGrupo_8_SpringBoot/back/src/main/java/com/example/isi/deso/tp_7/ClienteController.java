package com.example.isi.deso.tp_7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public Cliente crearCliente(@RequestBody Cliente cliente) {
        return clienteService.crearCliente(cliente);
    }

    @GetMapping("/{id}")
    public Optional<Cliente> buscarCliente(@PathVariable long id) {
        return clienteService.buscarCliente(id);
    }

    @PutMapping("/{id}")
    public Cliente modificarCliente(@PathVariable long id, @RequestBody Cliente cliente) {
        cliente.setId(id);
        return clienteService.modificarCliente(cliente);
    }

    @DeleteMapping("/{id}")
    public void eliminarCliente(@PathVariable long id) {
        clienteService.eliminarCliente(id);
    }

    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.listarClientes();
    }
}
