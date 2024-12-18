/*package com.example.isi.deso.tp_7;


import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ItemsPedidoMemory implements ItemsPedidoDao {
    private Set<ItemPedido> listaItems;

    public ItemsPedidoMemory() {
        this.listaItems = new HashSet<>();
    }

    public Set<ItemPedido> getLista() {
        return listaItems;
    }

    public Set<ItemPedido> agregarItem(ItemPedido ip) {
        if (this.listaItems == null) {
            this.listaItems = new HashSet<>();
        }
        if (ip != null) {
            this.listaItems.add(ip);
        }
        return listaItems;
    }

    public Set<ItemPedido> agregarItem(ItemMenu im) {
        if (this.listaItems == null) {
            this.listaItems = new HashSet<>();
        }
        if (im != null) {
            ItemPedido ip = new ItemPedido(im);
            this.listaItems.add(ip);
        }
        return listaItems;
    }

    public Set<ItemPedido> filtrado(char i) throws ItemNoEncontradoException {
        Set<ItemPedido> aux = new HashSet<>();
        switch (i) {
            case 'V':
                aux = this.listaItems.stream().filter(item -> item.getItemPedido().aptoVegano()).collect(Collectors.toSet());
                break;
            case 'B':
                aux = this.listaItems.stream().filter(item -> item.getItemPedido().esBebida()).collect(Collectors.toSet());
                break;
            case 'C':
                aux = this.listaItems.stream().filter(item -> item.getItemPedido().esComida()).collect(Collectors.toSet());
                break;
            case 'A':
                aux = this.listaItems.stream().filter(item -> item.getItemPedido().esAlcoholica()).collect(Collectors.toSet());
                break;
        }
        if (aux.isEmpty()) {
            throw new ItemNoEncontradoException("Item No encontrado");
        } else {
            StringBuilder mensaje = new StringBuilder("Los resultados son: [");
            for (ItemPedido p : aux) {
                mensaje.append(p.getItemPedido()).append(", ");
            }
            mensaje.setLength(mensaje.length() - 2); // Elimina la última coma y el espacio
            mensaje.append("]");
            System.out.println(mensaje);
            return aux;
        }
    }

    @Override
    public Set<ItemPedido> ordenarPorCriterio() {
        Set<ItemPedido> setAux = this.listaItems.stream()
                .sorted(Comparator.comparing(item -> item.getItemPedido().toString()))
                .collect(Collectors.toSet());

        StringBuilder resultado = new StringBuilder("El orden alfabetico de los items es: [");
        for (ItemPedido item : setAux) {
            resultado.append(item.getItemPedido()).append(", ");
        }
        if (!setAux.isEmpty()) {
            resultado.setLength(resultado.length() - 2); // Elimina la última coma y el espacio
        }
        resultado.append("]");
        System.out.println(resultado.toString());

        return setAux;
    }

    @Override
    public Set<ItemPedido> busquedaPorRangodePecios(double min, double max) throws ItemNoEncontradoException {
        Set<ItemPedido> setAux = this.listaItems.stream()
                .filter(item -> item.getItemPedido().getPrecio() > min && item.getItemPedido().getPrecio() < max)
                .collect(Collectors.toSet());
        if (setAux.isEmpty()) {
            throw new ItemNoEncontradoException("Item No encontrado");
        } else {
            StringBuilder resultado = new StringBuilder("El/Lost item que cumplen la condicion de min " + min + " a " + max + " : [");
            for (ItemPedido item : setAux) {
                resultado.append(item.getItemPedido()).append(", ");
            }
            if (!setAux.isEmpty()) {
                resultado.setLength(resultado.length() - 2); // Elimina la última coma y el espacio
            }
            resultado.append("]");
            System.out.println(resultado.toString());
        }
        return setAux;
    }

    @Override
    public Set<ItemPedido> buscarPorRestaurante(Vendedor v) throws ItemNoEncontradoException {
        Set<ItemPedido> set1 = this.listaItems.stream()
                .filter(items -> v.getList().contains(items.getItemPedido()))
                .collect(Collectors.toSet());
        if (set1.isEmpty()) {
            throw new ItemNoEncontradoException("Item No encontrado");
        } else {
            StringBuilder resultado = new StringBuilder("El vendedor " + v.getNombre() + " dispone de: [");
            for (ItemPedido item : set1) {
                resultado.append(item.getItemPedido()).append(", ");
            }
            if (!set1.isEmpty()) {
                resultado.setLength(resultado.length() - 2); // Elimina la última coma y el espacio
            }
            resultado.append("]");
            System.out.println(resultado.toString());
        }
        return set1;
    }

    public double calcularTotal() {
        if (this.listaItems == null) {
            return 0.0;
        }
        double total = listaItems.stream().mapToDouble(item -> item.getItemPedido().getPrecio()).sum();
        return total;
    }
}
/* */