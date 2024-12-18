package com.example.isi.deso.tp_7;

public class ClienteDTO {
    private boolean deleted;

    public ClienteDTO(Cliente cliente) {
        this.deleted = cliente.isDeleted();
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
