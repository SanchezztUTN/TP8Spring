package com.example.isi.deso.tp_7;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    @JsonIgnoreProperties({"pedidos"})
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pedido")
    @JsonManagedReference
    private List<ItemPedido> itemsPedidoMemory = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pago_id")
    @JsonIgnore
    private Pago pago;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vendedor_id")
    @JsonIgnoreProperties({"pedidos"})
    private Vendedor vendedor;

    @Enumerated(EnumType.STRING)
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    @Transient
    @JsonIgnore
    private List<Observer> observers = new ArrayList<>();

    private String metodoDePago;

    private double totalPrice;

    public Pedido() {
    }

    public Pedido(long id, Cliente cliente, List<ItemPedido> itemsPedidoMemory, Vendedor vendedor, String metodoDePago) {
        this.id = id;
        this.cliente = cliente;
        this.itemsPedidoMemory = itemsPedidoMemory;
        this.vendedor = vendedor;
        this.metodoDePago = metodoDePago;
        this.estado = EstadoPedido.PENDIENTE;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedido> getItemsPedidoMemory() {
        return itemsPedidoMemory;
    }

    public void setItemsPedidoMemory(List<ItemPedido> itemsPedidoMemory) {
        this.itemsPedidoMemory = itemsPedidoMemory;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public Vendedor getVendedor() {
        return vendedor;
    }

    public void setVendedor(Vendedor vendedor) {
        this.vendedor = vendedor;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
        notificarObservers();
    }

    public List<Observer> getObservers() {
        return observers;
    }

    public void agregarObserver(Observer observer) {
        observers.add(observer);
    }

    public void eliminarObserver(Observer observer) {
        observers.remove(observer);
    }

    private void notificarObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public String getMetodoDePago() {
        return metodoDePago;
    }

    public void setMetodoDePago(String metodoDePago) {
        this.metodoDePago = metodoDePago;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}