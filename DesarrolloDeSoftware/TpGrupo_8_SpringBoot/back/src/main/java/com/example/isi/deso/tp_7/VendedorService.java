package com.example.isi.deso.tp_7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
public class VendedorService {
    @Autowired
    private VendedorRepository vendedorRepository;

    @Autowired
    private CoordenadaDAO coordenadaDAO;

    @Autowired
    private ItemMenuRepository itemMenuRepository;

    public Vendedor crearVendedor(Vendedor vendedor) {
        // Persist the Coordenada entity first
        Coordenada coordenada = vendedor.getCoordenada();
        if (coordenada != null) {
            coordenada = coordenadaDAO.save(coordenada);
            vendedor.setCoordenada(coordenada);
        }

        // Persist the ItemMenu entities
        Set<ItemMenu> items = vendedor.getList();
        if (items != null) {
            Set<ItemMenu> savedItems = new HashSet<>();
            for (ItemMenu item : items) {
                savedItems.add(itemMenuRepository.save(item));
            }
            vendedor.setListaProductos(savedItems);
        }

        return vendedorRepository.save(vendedor);
    }

    public Optional<Vendedor> buscarVendedor(long id) {
        return vendedorRepository.findById(id);
    }

    public Vendedor modificarVendedor(Vendedor vendedor) {
        Coordenada coordenada = vendedor.getCoordenada();
        if (coordenada != null) {
            coordenada = coordenadaDAO.save(coordenada);
            vendedor.setCoordenada(coordenada);
        }

        Set<ItemMenu> items = vendedor.getList();
        if (items != null) {
            Set<ItemMenu> savedItems = new HashSet<>();
            for (ItemMenu item : items) {
                savedItems.add(itemMenuRepository.save(item));
            }
            vendedor.setListaProductos(savedItems);
        }

        return vendedorRepository.save(vendedor);
    }

    public void eliminarVendedor(long id) {
        vendedorRepository.deleteById(id);
    }

    public List<Vendedor> listarVendedores() {
        return vendedorRepository.findAll();
    }

    public Vendedor agregarItemMenu(long id, ItemMenu itemMenu) {
        Optional<Vendedor> vendedorOpt = vendedorRepository.findById(id);
        if (vendedorOpt.isPresent()) {
            Vendedor vendedor = vendedorOpt.get();
            vendedor.getList().add(itemMenu);
            return vendedorRepository.save(vendedor);
        }
        return null;
    }

    public Set<ItemMenu> listarItemsMenu(long id) {
        Optional<Vendedor> vendedorOpt = vendedorRepository.findById(id);
        if (vendedorOpt.isPresent()) {
            return vendedorOpt.get().getList();
        }
        return null;
    }
}