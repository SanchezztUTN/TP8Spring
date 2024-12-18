package com.example.isi.deso.tp_7;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String cuit;
    private String email;
    private String direccion;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coordenada_id")
    private Coordenada coor;
    
    private String alias;
    private String cbu;
    private boolean deleted = false;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Pedido> pedidos;

    public Cliente() {
        // Constructor vacío
    }

    public Cliente(long id, String cuit, String email, String direccion, Coordenada coor, String alias, String cbu) {
        this.id = id;
        this.cuit = cuit;
        this.email = email;
        this.direccion = direccion;
        this.coor = coor;
        this.alias = alias;
        this.cbu = cbu;
    }

    // Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCuit() {
        return cuit;
    }

    public void setCuit(String cuit) {
        this.cuit = cuit;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Coordenada getCoor() {
        return coor;
    }

    public void setCoor(Coordenada coor) {
        this.coor = coor;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCbu() {
        return cbu;
    }

    public void setCbu(String cbu) {
        this.cbu = cbu;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
}