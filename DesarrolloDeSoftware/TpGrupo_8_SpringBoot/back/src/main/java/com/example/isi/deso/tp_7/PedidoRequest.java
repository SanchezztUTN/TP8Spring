package com.example.isi.deso.tp_7;

import java.util.List;

public class PedidoRequest {
    private long vendedorId;
    private long clienteId;
    private List<Long> itemMenuIds;
    private String tipoPago;

    // Getters and Setters
    public long getVendedorId() {
        return vendedorId;
    }

    public void setVendedorId(long vendedorId) {
        this.vendedorId = vendedorId;
    }

    public long getClienteId() {
        return clienteId;
    }

    public void setClienteId(long clienteId) {
        this.clienteId = clienteId;
    }

    public List<Long> getItemMenuIds() {
        return itemMenuIds;
    }

    public void setItemMenuIds(List<Long> itemMenuIds) {
        this.itemMenuIds = itemMenuIds;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }
}
