package com.example.isi.deso.tp_7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/vendedores")
public class VendedorController {
    @Autowired
    private VendedorService vendedorService;

    @PostMapping
    public Vendedor crearVendedor(@RequestBody Vendedor vendedor) {
        return vendedorService.crearVendedor(vendedor);
    }

    @GetMapping("/{id}")
    public Optional<Vendedor> buscarVendedor(@PathVariable long id) {
        return vendedorService.buscarVendedor(id);
    }

    @PutMapping("/{id}")
    public Vendedor modificarVendedor(@PathVariable long id, @RequestBody Vendedor vendedor) {
        vendedor.setId(id);
        return vendedorService.modificarVendedor(vendedor);
    }

    @DeleteMapping("/{id}")
    public void eliminarVendedor(@PathVariable long id) {
        vendedorService.eliminarVendedor(id);
    }

    @GetMapping
    public List<Vendedor> listarVendedores() {
        return vendedorService.listarVendedores();
    }

    @PostMapping("/{id}/items")
    public Vendedor agregarItemMenu(@PathVariable long id, @RequestBody ItemMenu itemMenu) {
        return vendedorService.agregarItemMenu(id, itemMenu);
    }

    @GetMapping("/{id}/items")
    public Set<ItemMenu> listarItemsMenu(@PathVariable long id) {
        return vendedorService.listarItemsMenu(id);
    }

    @DeleteMapping("/{vendedorId}/items/{itemId}")
    public Vendedor eliminarItemMenu(@PathVariable long vendedorId, @PathVariable long itemId) {
        return vendedorService.eliminarItemMenu(vendedorId, itemId);
    }
}