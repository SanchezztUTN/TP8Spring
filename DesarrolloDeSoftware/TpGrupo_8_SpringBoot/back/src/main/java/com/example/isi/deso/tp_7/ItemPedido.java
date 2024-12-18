/*package com.example.isi.deso.tp_7;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_menu_id")
    private ItemMenu renglonPedido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    @JsonBackReference
    private Pedido pedido;

    public ItemPedido() {
    }

    public ItemPedido(ItemMenu renglonPedido) {
        this.renglonPedido = renglonPedido;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ItemMenu getItemPedido() {
        return renglonPedido;
    }

    public void setItemPedido(ItemMenu renglonPedido) {
        this.renglonPedido = renglonPedido;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }
}
    /**/