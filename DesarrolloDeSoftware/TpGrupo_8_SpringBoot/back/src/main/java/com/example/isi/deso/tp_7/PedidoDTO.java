package com.example.isi.deso.tp_7;

import java.util.Set;

public class PedidoDTO {
    private long id;
    private Cliente cliente;
    private Set<ItemMenu> items;
    private Pago pago;
    private VendedorDTO vendedor;
    private EstadoPedido estado;
    private String metodoDePago;
    private double totalPrice;

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.cliente = pedido.getCliente();
        this.items = pedido.getItems();
        this.pago = pedido.getPago();
        this.vendedor = new VendedorDTO(pedido.getVendedor());
        this.estado = pedido.getEstado();
        this.metodoDePago = pedido.getMetodoDePago();
        this.totalPrice = pedido.getTotalPrice();
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

    public Set<ItemMenu> getItems() {
        return items;
    }

    public void setItems(Set<ItemMenu> items) {
        this.items = items;
    }

    public Pago getPago() {
        return pago;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }

    public VendedorDTO getVendedor() {
        return vendedor;
    }

    public void setVendedor(VendedorDTO vendedor) {
        this.vendedor = vendedor;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
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
