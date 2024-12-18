package com.example.isi.deso.tp_7;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/items")
public class ItemsMenuController {
    @Autowired
    private ItemMenuService itemMenuService;

    @PostMapping("/plato")
    public String crearPlato(@RequestBody Plato plato) {
        return itemMenuService.crearItem(plato);
    }

    @PostMapping("/bebida")
    public String crearBebida(@RequestBody Bebida bebida) {
        return itemMenuService.crearItem(bebida);
    }

    @GetMapping("/{id}")
    public Optional<ItemMenu> buscarItem(@PathVariable long id) {
        return itemMenuService.buscarItem(id);
    }

    @GetMapping
    public List<ItemMenu> listarItems() {
        return itemMenuService.listarItems();
    }

    @PutMapping("/{id}")
    public String modificarItem(@PathVariable long id, @RequestBody ItemMenu item) {
        item.setId(id);
        return itemMenuService.actualizarItem(item);
    }

    @DeleteMapping("/{id}")
    public void eliminarItem(@PathVariable long id) throws ItemNoEncontradoException {
        itemMenuService.eliminarItem(id);
    }
}
