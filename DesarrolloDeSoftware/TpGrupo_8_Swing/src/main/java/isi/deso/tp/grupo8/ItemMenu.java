package isi.deso.tp.grupo8;

public abstract class ItemMenu {
    private long id;
    public String nombre;
    private String descripcion;
    public double precio;
    private Categoria categoria;

    public abstract double peso(double p);
    public abstract boolean esComida();
    public abstract boolean esBebida();
    public abstract boolean aptoVegano();
    public abstract boolean esAlcoholica();
    public abstract String toString();
    public abstract double getPrecio();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesc() {
        return descripcion;
    }

    public void setDesc(String descripcion) {
        this.descripcion = descripcion;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
