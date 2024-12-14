package isi.deso.tp.grupo8;

public class Categoria {
    private long id_categoria;
    private String descripcion;
    private TipoItem item;

    public Categoria(long id_categoria) {
        this.id_categoria = id_categoria;
    }
    public Categoria() {
        
    }
    public long getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(long id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoItem getItem() {
        return item;
    }

    public void setItem(TipoItem item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
