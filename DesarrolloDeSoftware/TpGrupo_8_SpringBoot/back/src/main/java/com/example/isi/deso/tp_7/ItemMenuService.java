package com.example.isi.deso.tp_7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemMenuService {
    @Autowired
    private ItemMenuRepository itemMenuRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public String crearItem(ItemMenu item) {
        // Check if the Categoria exists
        Categoria categoria = item.getCategoria();
        if (categoria != null && categoria.getId_categoria() != 0) {
            Optional<Categoria> existingCategoria = categoriaRepository.findById(categoria.getId_categoria());
            if (existingCategoria.isPresent()) {
                item.setCategoria(existingCategoria.get());
            } else {
                throw new IllegalArgumentException("Categoria with ID " + categoria.getId_categoria() + " does not exist.");
            }
        }
        itemMenuRepository.save(item);
        return item instanceof Bebida ? "Bebida Creada" : "Plato Creado";
    }

    public Optional<ItemMenu> buscarItem(long id) {
        return itemMenuRepository.findById(id);
    }

    public String actualizarItem(ItemMenu item) {
        // Check if the Categoria exists
        Categoria categoria = item.getCategoria();
        if (categoria != null && categoria.getId_categoria() != 0) {
            Optional<Categoria> existingCategoria = categoriaRepository.findById(categoria.getId_categoria());
            if (existingCategoria.isPresent()) {
                item.setCategoria(existingCategoria.get());
            } else {
                throw new IllegalArgumentException("Categoria with ID " + categoria.getId_categoria() + " does not exist.");
            }
        }
        itemMenuRepository.save(item);
        return "Item Actualizado";
    }

    public void eliminarItem(long id) {
        itemMenuRepository.deleteById(id);
    }

    public List<ItemMenu> listarItems() {
        return itemMenuRepository.findAll();
    }
}
