package isi.deso.tp.grupo8;

import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private long id;
    private Cliente cliente;
    private ItemsPedidoMemory itemsPedidoMemory;
    private Pago pago;
    private Vendedor vendedor;
    private EstadoPedido estado;
    private List<Observer> observers;
    private String metodoDePago;

    public Pedido() {
        this.observers = new ArrayList<>();
    }

    public Pedido(long id, Cliente cliente, ItemsPedidoMemory itemsPedidoMemory, Vendedor vendedor, String metodoDePago) {
        this.id = id;
        this.cliente = cliente;
        this.itemsPedidoMemory = itemsPedidoMemory;
        this.vendedor = vendedor;
        this.metodoDePago = metodoDePago;
        this.estado = EstadoPedido.PENDIENTE;
        this.observers = new ArrayList<>();
    }

    // Métodos getters y setters

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

    public ItemsPedidoMemory getItemsPedidoMemory() {
        return itemsPedidoMemory;
    }

    public void setItemsPedidoMemory(ItemsPedidoMemory itemsPedidoMemory) {
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
}
