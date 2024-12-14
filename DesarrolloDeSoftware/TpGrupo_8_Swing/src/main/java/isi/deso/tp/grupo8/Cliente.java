package isi.deso.tp.grupo8;

public class Cliente {
    private long id;
    private String cuit;
    private String email;
    private String direccion;
    private Coordenada coordenadas;
    private String alias;
    private String cbu;
    
    public Cliente(long id, String cuit, String email, String direc, Coordenada coor, String alias, String cbu){
        this.id = id;
        this.cuit = cuit;
        this.email = email;
        this.direccion = direc;
        this.coordenadas = coor;
        this.alias = alias;
        this.cbu = cbu;
    }
    public void setId(long id){
        this.id=id;
    }
    public long getId(){
        return id;
    }
    
     public String getCuit(){
        return cuit;
    }
     
     public String getEmail(){
        return email;
    }
     
     public String getDireccion(){
        return direccion;
    }
     
     public Coordenada getCoor(){
        return coordenadas;
    }
     
     public String getAlias(){
        return alias;
    }
     
     public String getCbu(){
        return cbu;
    }


public void setCuit(String cuit) {
    this.cuit = cuit;
}

public void setEmail(String email) {
    this.email = email;
}

public void setDireccion(String direccion) {
    this.direccion = direccion;
}

public void setCoor(Coordenada coordenadas) {
    this.coordenadas = coordenadas;
}

public void setAlias(String alias) {
    this.alias = alias;
}

public void setCbu(String cbu) {
    this.cbu = cbu;
}
}